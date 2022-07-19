package com.linkapital.core.services.commission.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.commission.CommissionCampaignService;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionCampaignTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignAttributeTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignTO;
import com.linkapital.core.services.commission.contract.to.update.UpdateCommissionCampaignTO;
import com.linkapital.core.services.commission.datasource.CommissionCampaignAttributeRepository;
import com.linkapital.core.services.commission.datasource.CommissionCampaignRepository;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaign;
import com.linkapital.core.services.offer.OfferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.commission.contract.CommissionBinder.COMMISSION_BINDER;
import static com.linkapital.core.services.commission.contract.CommissionBinder.filterMatch;
import static com.linkapital.core.services.offer.contract.enums.OfferState.CONTRACT_SIGNED;
import static com.linkapital.core.services.offer.validator.OfferValidator.validateState;

@Service
@Transactional
public class CommissionCampaignServiceImpl implements CommissionCampaignService {

    private final CommissionCampaignRepository commissionCampaignRepository;
    private final CommissionCampaignAttributeRepository commissionCampaignAttributeRepository;
    private final OfferService offerService;

    public CommissionCampaignServiceImpl(CommissionCampaignRepository commissionCampaignRepository,
                                         CommissionCampaignAttributeRepository commissionCampaignAttributeRepository,
                                         OfferService offerService) {
        this.commissionCampaignRepository = commissionCampaignRepository;
        this.commissionCampaignAttributeRepository = commissionCampaignAttributeRepository;
        this.offerService = offerService;
    }

    @Override
    public CommissionCampaignTO create(CreateCommissionCampaignTO to) {
        return Optional
                .of(COMMISSION_BINDER.bind(to))
                .map(commissionCampaign -> COMMISSION_BINDER.bind(
                        commissionCampaignRepository.save(commissionCampaign)))
                .orElse(null);
    }

    @Override
    public CommissionCampaignTO update(UpdateCommissionCampaignTO to) throws UnprocessableEntityException {
        return Optional
                .of(COMMISSION_BINDER.set(to, getById(to.getId())))
                .map(commissionCampaign -> COMMISSION_BINDER.bind(
                        commissionCampaignRepository.save(commissionCampaign)))
                .orElse(null);
    }

    @Override
    public CommissionCampaign getById(long id) throws UnprocessableEntityException {
        return commissionCampaignRepository
                .findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("commission.not.found", id)));
    }

    @Override
    public CommissionCampaign getMatchBase() throws UnprocessableEntityException {
        return commissionCampaignRepository
                .findByBaseIsTrue()
                .orElseThrow(() -> new UnprocessableEntityException(msg("commission.not.found")));
    }

    @Override
    public List<CommissionCampaignTO> getAll() {
        return COMMISSION_BINDER.bind(commissionCampaignRepository.findAll());
    }

    @Override
    public List<CommissionCampaignTO> getAllMatchByOffer(long offerId) throws UnprocessableEntityException {
        var offer = offerService.getById(offerId);
        validateState(offer.getState(), CONTRACT_SIGNED);
        var campaigns = filterMatch.apply(offer, commissionCampaignRepository.findAll());

        return COMMISSION_BINDER.bind(campaigns);
    }

    @Override
    public List<CommissionCampaignAttributeTO> getAllAttributes() {
        return COMMISSION_BINDER.bindToAttributeList(commissionCampaignAttributeRepository.findAll());
    }

    @Override
    public void delete(long id) throws UnprocessableEntityException {
        commissionCampaignRepository.delete(getById(id));
    }

}
