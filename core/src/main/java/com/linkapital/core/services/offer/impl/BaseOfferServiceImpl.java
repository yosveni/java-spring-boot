package com.linkapital.core.services.offer.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.BankNomenclatureService;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankNomenclatureTO;
import com.linkapital.core.services.company.contract.to.SelectIndicativeOfferTO;
import com.linkapital.core.services.company_user.CompanyUserService;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.RequestOfferTO;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.indicative_offer.IndicativeOfferService;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOffer;
import com.linkapital.core.services.notification.EmailService;
import com.linkapital.core.services.notification.WebsocketService;
import com.linkapital.core.services.offer.OfferService;
import com.linkapital.core.services.offer.contract.to.AcceptOfferTO;
import com.linkapital.core.services.offer.contract.to.AcceptPartnerOfferTO;
import com.linkapital.core.services.offer.contract.to.CreateOfferLogTO;
import com.linkapital.core.services.offer.contract.to.UpdateOfferStateTO;
import com.linkapital.core.services.offer.contract.to.create.CreateOfferTO;
import com.linkapital.core.services.offer.contract.to.get.OfferTO;
import com.linkapital.core.services.offer.contract.to.update.UpdateOfferTO;
import com.linkapital.core.services.offer.datasource.OfferRepository;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import com.linkapital.core.services.offer.datasource.domain.OfferStateLogs;
import com.linkapital.core.services.partner_bank.PartnerBankService;
import com.linkapital.core.services.security.UserService;
import com.linkapital.core.services.security.datasource.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.comment.contract.enums.CommentArea.CLIENT;
import static com.linkapital.core.services.comment.contract.enums.LearningSession.OFFER;
import static com.linkapital.core.services.company.contract.CompanyBinder.COMPANY_BINDER;
import static com.linkapital.core.services.company.contract.CompanyBinder.buildSelectedOffer;
import static com.linkapital.core.services.company.contract.CompanyBinder.getIndicativeOfferByNumber;
import static com.linkapital.core.services.company.contract.CompanyBinder.setIndicativeOfferState;
import static com.linkapital.core.services.company.contract.enums.CreditApplicationFlow.CONTRACTED;
import static com.linkapital.core.services.company_user.validator.CompanyUserValidator.validateSelectedOffer;
import static com.linkapital.core.services.directory.contract.DirectoryBinder.DIRECTORY_BINDER;
import static com.linkapital.core.services.directory.contract.enums.Type.SIGNED_CONTRACT;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.OFFER_REQUEST;
import static com.linkapital.core.services.notification.WebsocketService.OFFER_NOTIFICATION;
import static com.linkapital.core.services.notification.WebsocketService.OFFER_STATUS_CHANGED;
import static com.linkapital.core.services.notification.contract.enums.EmailType.PROPOSAL;
import static com.linkapital.core.services.notification.contract.enums.WebsocketBroker.TOPIC;
import static com.linkapital.core.services.offer.contract.OfferBinder.OFFER_BINDER;
import static com.linkapital.core.services.offer.contract.OfferBinder.buildOfferByType;
import static com.linkapital.core.services.offer.contract.OfferBinder.buildUpdatedOffer;
import static com.linkapital.core.services.offer.contract.enums.OfferState.ACCEPTED;
import static com.linkapital.core.services.offer.contract.enums.OfferState.CONTRACT_SIGNED;
import static com.linkapital.core.services.offer.contract.enums.OfferState.INIT;
import static com.linkapital.core.services.offer.contract.enums.OfferState.REJECTED;
import static com.linkapital.core.services.offer.validator.OfferValidator.validateAcceptOffer;
import static com.linkapital.core.services.offer.validator.OfferValidator.validateChangeState;
import static com.linkapital.core.services.offer.validator.OfferValidator.validateIndicativeOffer;
import static com.linkapital.core.services.offer.validator.OfferValidator.validateOwnerAuthorization;
import static com.linkapital.core.services.security.contract.enums.Authority.ENTREPRENEUR;
import static com.linkapital.core.services.security.contract.enums.Authority.PARTNER;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_BACK;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_SEC;
import static com.linkapital.core.services.shared.contract.enums.Learned.getLearned;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public abstract class BaseOfferServiceImpl implements OfferService {

    protected final UserService userService;
    protected final CompanyUserService companyUserService;
    protected final PartnerBankService partnerBankService;
    protected final IndicativeOfferService indicativeOfferService;
    protected final DirectoryService directoryService;
    protected final OfferRepository offerRepository;
    protected final EmailService emailService;
    protected final BankNomenclatureService bankNomenclatureService;
    protected final WebsocketService webSocketService;

    protected BaseOfferServiceImpl(UserService userService,
                                   CompanyUserService companyUserService,
                                   PartnerBankService partnerBankService,
                                   IndicativeOfferService indicativeOfferService,
                                   DirectoryService directoryService,
                                   OfferRepository offerRepository,
                                   EmailService emailService,
                                   BankNomenclatureService bankNomenclatureService,
                                   WebsocketService webSocketService) {
        this.userService = userService;
        this.companyUserService = companyUserService;
        this.partnerBankService = partnerBankService;
        this.indicativeOfferService = indicativeOfferService;
        this.directoryService = directoryService;
        this.offerRepository = offerRepository;
        this.emailService = emailService;
        this.bankNomenclatureService = bankNomenclatureService;
        this.webSocketService = webSocketService;
    }

    @Override
    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public List<OfferTO> create(List<CreateOfferTO> to) throws UnprocessableEntityException {
        var offers = new ArrayList<OfferTO>();
        for (CreateOfferTO createOfferTO : to)
            offers.add(create(createOfferTO));

        return offers;
    }

    @Override
    public OfferTO createNotification(CreateOfferLogTO to) throws UnprocessableEntityException {
        var offer = getById(to.getOfferId());
        offer.getOfferStateLogs().add(new OfferStateLogs()
                .withOfferState(offer.getState())
                .withNotification(to.getNotification()));
        var offerTO = OFFER_BINDER.bind(save(offer));

        webSocketService.dispatch(
                offer.getUser().getEmail(),
                TOPIC,
                OFFER_NOTIFICATION,
                msg("offer.notification.created", getLearned(offer.getType())),
                offerTO);

        return offerTO;
    }

    @Override
    public OfferTO update(UpdateOfferTO to) throws UnprocessableEntityException {
        var offer = getById(to.getId());
        var user = offer.getUser();
        var partnerBank = partnerBankService.getById(to.getPartnerBank());

        offer = buildUpdatedOffer.apply(offer, to)
                .withPartnerBank(partnerBank);
        var offerTO = OFFER_BINDER.bind(save(offer));

        webSocketService.dispatch(
                user.getEmail(),
                TOPIC,
                OFFER_STATUS_CHANGED,
                msg("offer.updated", getLearned(offer.getType())),
                offerTO);

        return offerTO;
    }

    @Override
    public CompanyClientTO selectIndicativeOffer(SelectIndicativeOfferTO to) throws UnprocessableEntityException {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var companyUser = companyUserService.get(to.getCnpj(), user.getId());

        validateSelectedOffer(to);
        setIndicativeOfferState(companyUser, to);
        companyUser = companyUserService.save(companyUser);

        var client = COMPANY_BINDER.bindToClientTO(companyUser);
        var authority = user.getRole().getAuthority();
        if (authority.equals(PARTNER) || authority.equals(ENTREPRENEUR))
            client.withBankNomenclature(bankNomenclatureService.getAllByCompanyUser(companyUser));

        return client;
    }

    @Override
    public CompanyClientTO requestOffer(RequestOfferTO to) throws UnprocessableEntityException {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var companyUser = companyUserService.get(to.getCnpj(), user.getId());
        validateOwnerAuthorization(companyUser);
        var company = companyUser.getCompany();

        for (var number : to.getNumbers()) {
            var indicativeOffer = getIndicativeOfferByNumber.apply(companyUser, number);
            validateIndicativeOffer(indicativeOffer);
            indicativeOffer.setState(OFFER_REQUEST);
        }

        companyUser = companyUserService.save(companyUser);

        var client = COMPANY_BINDER.bindToClientTO(companyUser);
        if (user.getRole().getAuthority().equals(PARTNER))
            client.withBankNomenclature(bankNomenclatureService.getAllByCompanyUser(companyUser));

        var selectIndicativeOffer = buildSelectedOffer.apply(companyUser);
        emailService.send(PROPOSAL, user.getEmail(), user.getCompletePhone(), company.getMainInfo().getCnpj(),
                company.getMainInfo().getSocialReason(), company.getFantasyName(), selectIndicativeOffer);

        return client;
    }

    @Override
    public CompanyClientTO acceptOffer(AcceptOfferTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj());
        validateOwnerAuthorization(companyUser);
        var offers = offerRepository.findAllByIdIn(to.getIds());

        for (Offer offer : offers) {
            validateAcceptOffer(offer, companyUser);
            offer.getOfferStateLogs().add(new OfferStateLogs()
                    .withOfferState(ACCEPTED));
            offer.setState(ACCEPTED);

            webSocketService.dispatch(
                    asList(LKP_BACK, LKP_SEC),
                    TOPIC,
                    OFFER_STATUS_CHANGED,
                    msg("offer.status.accepted", getLearned(offer.getType())),
                    OFFER_BINDER.bind(offer));
        }

        return COMPANY_BINDER.bindToClientTO(companyUserService.save(companyUser));
    }

    @Override
    public OfferTO acceptPartnerOffer(AcceptPartnerOfferTO to) throws UnprocessableEntityException {
        return Optional
                .of(getById(to.getId()))
                .map(offer -> OFFER_BINDER.bind(save(offer
                        .withAccepted(to.isAccepted()))))
                .orElse(null);
    }

    @Override
    public OfferTO updateState(UpdateOfferStateTO to) throws UnprocessableEntityException {
        var offer = getById(to.getId());
        if (!to.getState().equals(REJECTED))
            validateChangeState(offer);

        offer = save(offer.withState(to.getState()));

        return OFFER_BINDER.bind(offer);
    }

    @Override
    public List<CompanyBankNomenclatureTO> getAllNomenclaturesById(long id) throws UnprocessableEntityException {
        var offer = getById(id);
        var companyUser = companyUserService.get(offer.getCnpj(), offer.getUser().getId());
        return bankNomenclatureService.getAllByOffer(offer, companyUser);
    }

    @Override
    public Offer getById(Long id) throws UnprocessableEntityException {
        return offerRepository.findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("offer.not.found", id)));
    }

    @Override
    public List<OfferTO> getAll() {
        return OFFER_BINDER.bind(offerRepository.findAll());
    }

    @Override
    public List<OfferTO> getAllByCnpj(String cnpj) {
        return OFFER_BINDER.bind(offerRepository.findAllByCnpj(cnpj));
    }

    @Override
    public List<Offer> getAllByUserIdAndCommissionIsNotNull(long userId) {
        return offerRepository.findAllByUserIdAndCommissionIsNotNull(userId);
    }

    @Override
    public List<DirectoryTO> getDocumentsForCommentsOffer(long id) throws UnprocessableEntityException {
        return Optional
                .of(getById(id))
                .flatMap(offer -> Optional
                        .of(offer.getComments()
                                .stream()
                                .filter(comment -> comment.getLearningSession().equals(OFFER) &&
                                        comment.getCommentArea().equals(CLIENT) &&
                                        !comment.getAttachments().isEmpty())
                                .flatMap(comment -> DIRECTORY_BINDER.bind(comment.getAttachments())
                                        .stream())
                                .toList()))
                .orElse(emptyList());
    }

    @Override
    public OfferTO uploadOfferContract(long offerId,
                                       Date contractDate,
                                       MultipartFile[] files) throws UnprocessableEntityException {
        var offer = getById(offerId);
        var user = offer.getUser();

        if (!offer.getDocuments().isEmpty())
            directoryService.deleteFiles(offer.getDocuments());

        var directories = directoryService.uploadFiles(offer.getCnpj(), files, SIGNED_CONTRACT);
        offer = save(offer
                .withState(CONTRACT_SIGNED)
                .withContractDate(contractDate)
                .withDocuments(directories));
        var offerTO = OFFER_BINDER.bind(offer);

        user.getUserGuide().setCreditApplicationFlow(CONTRACTED);
        userService.save(user);

        webSocketService.dispatch(
                user.getEmail(),
                TOPIC,
                OFFER_STATUS_CHANGED,
                msg("offer.status.contract.signed"),
                offerTO);

        return offerTO;
    }

    @Override
    public void delete(Long id) throws UnprocessableEntityException {
        Optional
                .of(getById(id))
                .ifPresent(offer -> {
                    directoryService.deleteFiles(offer.getDocuments());

                    if (offer.getIndicativeOffer() != null) {
                        offer.getIndicativeOffer().getOffers()
                                .removeIf(obj -> offer.getId().equals(obj.getId()));
                        indicativeOfferService.save(offer.getIndicativeOffer());
                    }

                    if (offer.getImage() != null)
                        directoryService.delete(offer.getImage().getUrl());

                    offerRepository.delete(offer);
                });
    }

    protected OfferTO create(CreateOfferTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), to.getUser());
        var indicativeOffer = getOffer(to.getType(), companyUser);
        var partnerBank = partnerBankService.getById(to.getPartnerBank());
        var offer = buildOfferByType.apply(to);

        if ("LINKAPITAL".equals(partnerBank.getName()))
            offer.setState(INIT);

        offer = save(offer
                .withPartnerBank(partnerBank)
                .withUser(companyUser.getUser())
                .withIndicativeOffer(indicativeOffer));

        indicativeOffer.getOffers().add(offer);

        var offerTO = OFFER_BINDER.bind(offer);

        webSocketService.dispatch(
                companyUser.getUser().getEmail(),
                TOPIC,
                OFFER_STATUS_CHANGED,
                msg("offer.created", getLearned(offer.getType())),
                offerTO);

        return offerTO;
    }

    protected IndicativeOffer getOffer(int type, CompanyUser companyUser) throws UnprocessableEntityException {
        return switch (type) {
            case 1 -> companyUser.getIndicativeOfferOne();
            case 2 -> companyUser.getIndicativeOfferTwo();
            case 3 -> companyUser.getIndicativeOfferThree();
            case 4 -> companyUser.getIndicativeOfferFour();
            default -> throw new UnprocessableEntityException(msg("offer.type.not.found"));
        };
    }

}
