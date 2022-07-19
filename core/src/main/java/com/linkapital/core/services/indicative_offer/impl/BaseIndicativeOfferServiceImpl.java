package com.linkapital.core.services.indicative_offer.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.indicative_offer.IndicativeOfferService;
import com.linkapital.core.services.indicative_offer.datasource.IndicativeOfferRepository;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOffer;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

public abstract class BaseIndicativeOfferServiceImpl implements IndicativeOfferService {

    protected final IndicativeOfferRepository indicativeOfferRepository;

    protected BaseIndicativeOfferServiceImpl(IndicativeOfferRepository indicativeOfferRepository) {
        this.indicativeOfferRepository = indicativeOfferRepository;
    }

    @Override
    public IndicativeOffer save(IndicativeOffer indicativeOffer) {
        return indicativeOfferRepository.save(indicativeOffer);
    }

    @Override
    public IndicativeOffer getById(long id) throws UnprocessableEntityException {
        return indicativeOfferRepository.findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("offer.not.found", id)));
    }

    @Override
    public CompanyUser getIndicativeOffer(int number, CompanyUser companyUser) {
        return getIndicativeOffer(companyUser);
    }

    protected CompanyUser getIndicativeOffer(CompanyUser companyUser) {
        return companyUser;
    }

}
