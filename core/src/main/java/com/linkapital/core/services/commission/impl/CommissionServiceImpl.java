package com.linkapital.core.services.commission.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.commission.CommissionCampaignService;
import com.linkapital.core.services.commission.CommissionService;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionInstallmentTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionUserTO;
import com.linkapital.core.services.commission.datasource.CommissionInstallmentRepository;
import com.linkapital.core.services.commission.datasource.CommissionRepository;
import com.linkapital.core.services.commission.datasource.domain.Commission;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaign;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.offer.OfferService;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import com.linkapital.core.services.security.datasource.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

import static com.linkapital.core.services.commission.contract.CommissionBinder.COMMISSION_BINDER;
import static com.linkapital.core.services.commission.contract.CommissionBinder.buildInstallments;
import static com.linkapital.core.services.commission.contract.CommissionBinder.buildListCommissionUserTO;
import static com.linkapital.core.services.commission.contract.CommissionBinder.calculatePercent;
import static com.linkapital.core.services.commission.contract.CommissionBinder.setCommissionCampaignLimit;
import static com.linkapital.core.services.commission.contract.CommissionBinder.setCommissionCampaignUser;
import static com.linkapital.core.services.directory.contract.enums.Type.NOTE_FISCAL;
import static com.linkapital.core.services.offer.contract.enums.OfferState.CONTRACT_SIGNED;
import static com.linkapital.core.services.offer.validator.OfferValidator.validateHasCommission;
import static com.linkapital.core.services.offer.validator.OfferValidator.validateState;
import static com.linkapital.core.util.json.JsonSerdes.jsonfy;

@Service
@Transactional
public class CommissionServiceImpl implements CommissionService {

    private final CommissionRepository commissionRepository;
    private final CommissionCampaignService commissionCampaignService;
    private final OfferService offerService;
    private final CommissionInstallmentRepository commissionInstallmentRepository;
    private final DirectoryService directoryService;

    public CommissionServiceImpl(CommissionRepository commissionRepository,
                                 CommissionCampaignService commissionCampaignService,
                                 OfferService offerService,
                                 CommissionInstallmentRepository commissionInstallmentRepository,
                                 DirectoryService directoryService) {
        this.commissionRepository = commissionRepository;
        this.commissionCampaignService = commissionCampaignService;
        this.offerService = offerService;
        this.commissionInstallmentRepository = commissionInstallmentRepository;
        this.directoryService = directoryService;
    }

    @Override
    public CommissionTO create(CreateCommissionTO to) throws UnprocessableEntityException {
        var campaign = commissionCampaignService.getById(to.getCampaignId());
        var offer = offerService.getById(to.getOfferId());
        validateHasCommission(offer);
        validateState(offer.getState(), CONTRACT_SIGNED);
        setCommissionCampaignLimit.accept(campaign);
        setCommissionCampaignUser.accept(campaign, offer.getUser());
        var commission = buildCommission(to.getReleaseDate(), offer, campaign);

        return COMMISSION_BINDER.bindToCommissionTO(save(commission));
    }

    @Override
    public List<CommissionUserTO> getAllCommissionByUserId(Long userId) {
        var id = userId == null
                ? ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
                : userId;
        var offers = offerService.getAllByUserIdAndCommissionIsNotNull(id);

        return buildListCommissionUserTO.apply(offers);
    }

    @Override
    public List<CommissionInstallmentTO> uploadFiscalNotesToCommissionInstalments(List<Long> commissionInstallmentIds, MultipartFile file) {

        var commissionInstallments = commissionInstallmentRepository.findAllByIdIn(commissionInstallmentIds);
        commissionInstallments.forEach(commissionInstallment -> {
            try {
                commissionInstallment.withDocument(directoryService.uploadFile(String.valueOf(commissionInstallment.getId()), file, NOTE_FISCAL));
            } catch (UnprocessableEntityException e) {
                e.printStackTrace();
            }
        });
        return COMMISSION_BINDER.bindToCommissionInstallmentList(commissionInstallmentRepository.saveAll(commissionInstallments));
    }

    //region Save commission
    private Commission save(Commission commission) {
        return commissionRepository.save(commission);
    }
    //endregion

    //region Build commission
    private Commission buildCommission(Date releaseDate,
                                       Offer offer,
                                       CommissionCampaign campaign) throws UnprocessableEntityException {
        var paymentPercent = campaign.getPaymentPercent();
        var paymentSize = offer.getPayments().size();
        var offerTotal = offer.getTotal();

        var percent = calculatePercent.applyAsDouble(paymentSize, campaign.getPercents());
        var commissionTotal = offerTotal * percent;
        var amortization = paymentPercent.getAmortization() * commissionTotal;
        var commission = new Commission()
                .withTotal(commissionTotal)
                .withDisbursement(paymentPercent.getDisbursement() * commissionTotal)
                .withAmortization(amortization)
                .withLiquidation(paymentPercent.getLiquidation() * commissionTotal)
                .withReleaseDate(releaseDate)
                .withCampaign(jsonfy(campaign));

        if (paymentSize > 0 && amortization > 0) {
            var amortizationBase = calculateAmortizationBase(campaign, paymentSize, amortization, offerTotal);
            var installments = buildInstallments.apply(paymentSize, amortizationBase, commission);
            commission.withInstallments(installments);
        }

        return commission;
    }
    //endregion

    //region calculates amortization and the base campaign if not calculates the amortization of the campaign itself
    private double calculateAmortizationBase(CommissionCampaign campaign,
                                             int paymentSize,
                                             double amortization,
                                             double offerTotal) throws UnprocessableEntityException {
        if (paymentSize == 0)
            return 0D;

        if (campaign.isBase())
            return amortization;

        var campaignBase = commissionCampaignService.getMatchBase();
        var percent = calculatePercent.applyAsDouble(paymentSize, campaignBase.getPercents());
        var commissionTotal = offerTotal * percent;

        return campaignBase.getPaymentPercent().getAmortization() * commissionTotal;
    }
    //endregion

}
