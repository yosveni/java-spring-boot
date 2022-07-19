package com.linkapital.core.services.indicative_offer.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.datasource.IndicativeOfferRepository;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferOne;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.LEARNING_OFFER_BINDER;
import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.set;
import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.setOfferOne;

@Service
@Transactional
public class IndicativeOfferOneServiceImpl extends BaseIndicativeOfferServiceImpl {

    public IndicativeOfferOneServiceImpl(IndicativeOfferRepository indicativeOfferRepository) {
        super(indicativeOfferRepository);
    }

    @Override
    public CompanyUser getIndicativeOffer(CompanyUser companyUser) {
        return setOfferOne.apply(companyUser);
    }

    @Override
    public IndicativeOfferTO updateIndicativeOffer(UpdateIndicativeOfferTO to) throws UnprocessableEntityException {
        var indicativeOffer = (IndicativeOfferOne) set.apply(getById(to.getId()), to);
        return LEARNING_OFFER_BINDER.bind(save(indicativeOffer));
    }

}
