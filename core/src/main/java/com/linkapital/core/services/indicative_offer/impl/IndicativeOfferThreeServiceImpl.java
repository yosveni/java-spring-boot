package com.linkapital.core.services.indicative_offer.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.configuration.ConfigurationService;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.datasource.IndicativeOfferRepository;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferThree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.LEARNING_OFFER_BINDER;
import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.set;
import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.setOfferThree;

@Service
@Transactional
public class IndicativeOfferThreeServiceImpl extends BaseIndicativeOfferServiceImpl {

    private final ConfigurationService configurationService;

    public IndicativeOfferThreeServiceImpl(IndicativeOfferRepository indicativeOfferRepository,
                                           ConfigurationService configurationService) {
        super(indicativeOfferRepository);
        this.configurationService = configurationService;
    }

    @Override
    public CompanyUser getIndicativeOffer(CompanyUser companyUser) {
        return setOfferThree.apply(configurationService.loadAll().getElements(), companyUser);
    }

    @Override
    public IndicativeOfferTO updateIndicativeOffer(UpdateIndicativeOfferTO to) throws UnprocessableEntityException {
        var indicativeOffer = (IndicativeOfferThree) set.apply(getById(to.getId()), to);
        return LEARNING_OFFER_BINDER.bind(save(indicativeOffer));
    }

}
