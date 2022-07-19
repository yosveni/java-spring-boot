package com.linkapital.core.services.indicative_offer.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.datasource.IndicativeOfferRepository;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferFour;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.LEARNING_OFFER_BINDER;
import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.set;
import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.setLearningOfferFour;

@Service
@Transactional
public class IndicativeOfferFourServiceImpl extends BaseIndicativeOfferServiceImpl {

    public IndicativeOfferFourServiceImpl(IndicativeOfferRepository indicativeOfferRepository) {
        super(indicativeOfferRepository);
    }

    @Override
    public CompanyUser getIndicativeOffer(CompanyUser companyUser) {
        return setLearningOfferFour.apply(companyUser);
    }

    @Override
    public IndicativeOfferTO updateIndicativeOffer(UpdateIndicativeOfferTO to) throws UnprocessableEntityException {
        var indicativeOffer = (IndicativeOfferFour) set.apply(getById(to.getId()), to);
        return LEARNING_OFFER_BINDER.bind(indicativeOffer);
    }

}
