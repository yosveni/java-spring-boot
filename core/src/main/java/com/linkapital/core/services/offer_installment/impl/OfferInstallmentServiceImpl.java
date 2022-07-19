package com.linkapital.core.services.offer_installment.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.notification.WebsocketService;
import com.linkapital.core.services.offer.OfferService;
import com.linkapital.core.services.offer_installment.OfferInstallmentService;
import com.linkapital.core.services.offer_installment.contract.to.CreateOfferInstallmentTO;
import com.linkapital.core.services.offer_installment.contract.to.OfferInstallmentTO;
import com.linkapital.core.services.offer_installment.contract.to.UpdateOfferInstallmentTO;
import com.linkapital.core.services.offer_installment.datasource.OfferInstallmentRepository;
import com.linkapital.core.services.offer_installment.datasource.domain.OfferInstallment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.directory.contract.enums.Type.PAYMENT_TICKET;
import static com.linkapital.core.services.notification.WebsocketService.INSTALLMENT_PAYMENT_TICKET_UPLOADED;
import static com.linkapital.core.services.notification.WebsocketService.INSTALLMENT_STATUS_CHANGED;
import static com.linkapital.core.services.notification.contract.enums.WebsocketBroker.TOPIC;
import static com.linkapital.core.services.offer.contract.enums.OfferState.CONTRACT_SIGNED;
import static com.linkapital.core.services.offer.validator.OfferValidator.validateState;
import static com.linkapital.core.services.offer_installment.contract.OfferInstallmentBinder.OFFER_INSTALLMENT_BINDER;
import static java.util.Comparator.comparing;
import static org.eclipse.jetty.util.StringUtil.valueOf;

@Service
@Transactional
public class OfferInstallmentServiceImpl implements OfferInstallmentService {

    private final OfferInstallmentRepository offerInstallmentRepository;
    private final OfferService offerService;
    private final DirectoryService directoryService;
    private final WebsocketService webSocketService;

    public OfferInstallmentServiceImpl(OfferInstallmentRepository offerInstallmentRepository,
                                       OfferService offerService,
                                       DirectoryService directoryService,
                                       WebsocketService webSocketService) {
        this.offerInstallmentRepository = offerInstallmentRepository;
        this.offerService = offerService;
        this.directoryService = directoryService;
        this.webSocketService = webSocketService;
    }

    @Override
    public List<OfferInstallmentTO> create(CreateOfferInstallmentTO to) throws UnprocessableEntityException {
        var offer = offerService.getById(to.getOfferId());
        var user = offer.getUser();
        validateState(offer.getState(), CONTRACT_SIGNED);

        var installments = OFFER_INSTALLMENT_BINDER.bind(to.getInstallments());
        var created = offerService.save(offer
                .withPayments(installments)
                .withUser(user));
        var payments = created.getPayments();
        var size = payments.size();
        var init = size - to.getInstallments().size();
        var list = offerInstallmentRepository
                .saveAll(new ArrayList<>(payments).subList(init, size))
                .stream()
                .sorted(comparing(OfferInstallment::getExpiration))
                .toList();
        var installmentsTO = OFFER_INSTALLMENT_BINDER.bindToList(list);

        webSocketService.dispatch(
                user.getEmail(),
                TOPIC,
                INSTALLMENT_STATUS_CHANGED,
                msg("installment.created"),
                OFFER_INSTALLMENT_BINDER.bindToNotificationList(list)
        );

        return installmentsTO;
    }

    @Override
    public OfferInstallmentTO update(UpdateOfferInstallmentTO to) throws UnprocessableEntityException {
        return Optional
                .of(getById(to.getId()))
                .map(offer -> {
                    var offerInstallment = save(OFFER_INSTALLMENT_BINDER.set(to, offer));

                    webSocketService.dispatch(
                            offer.getOffer().getUser().getEmail(),
                            TOPIC,
                            INSTALLMENT_STATUS_CHANGED,
                            msg("installment.updated"),
                            OFFER_INSTALLMENT_BINDER.bindToNotification(offerInstallment)
                    );

                    return OFFER_INSTALLMENT_BINDER.bind(offerInstallment);
                })
                .orElse(null);
    }

    @Override
    public OfferInstallmentTO uploadPaymentTicket(long id, MultipartFile file) throws UnprocessableEntityException {
        var offerInstallment = getById(id);
        var directory = directoryService.uploadFile(valueOf(id), file, PAYMENT_TICKET);
        offerInstallment = save(offerInstallment.withDocument(directory));

        webSocketService.dispatch(
                offerInstallment.getOffer().getUser().getEmail(),
                TOPIC,
                INSTALLMENT_PAYMENT_TICKET_UPLOADED,
                msg("installment.payment.ticket.uploaded"),
                OFFER_INSTALLMENT_BINDER.bindToNotification(offerInstallment)
        );

        return OFFER_INSTALLMENT_BINDER.bind(offerInstallment);
    }

    @Override
    public OfferInstallment getById(long id) throws UnprocessableEntityException {
        return offerInstallmentRepository
                .findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("offer.installment.not.found", id)));
    }

    @Override
    public void delete(long offerId, long id) throws UnprocessableEntityException {
        Optional
                .of(offerService.getById(offerId))
                .ifPresent(offer -> {
                    offer.getPayments().removeIf(offerInstallment -> offerInstallment.getId().equals(id));
                    offerService.save(offer);
                });
    }

    private OfferInstallment save(OfferInstallment offerInstallment) {
        return offerInstallmentRepository.save(offerInstallment);
    }

}
