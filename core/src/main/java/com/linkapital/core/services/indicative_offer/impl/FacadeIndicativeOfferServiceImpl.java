package com.linkapital.core.services.indicative_offer.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.indicative_offer.IndicativeOfferService;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOffer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FacadeIndicativeOfferServiceImpl implements IndicativeOfferService {

    private final IndicativeOfferOneServiceImpl indicativeOfferOneService;
    private final IndicativeOfferTwoServiceImpl indicativeOfferTwoService;
    private final IndicativeOfferThreeServiceImpl indicativeOfferThreeService;
    private final IndicativeOfferFourServiceImpl indicativeOfferFourService;
    private BaseIndicativeOfferServiceImpl service;

    public FacadeIndicativeOfferServiceImpl(IndicativeOfferOneServiceImpl indicativeOfferOneService,
                                            IndicativeOfferTwoServiceImpl indicativeOfferTwoService,
                                            IndicativeOfferThreeServiceImpl indicativeOfferThreeService,
                                            IndicativeOfferFourServiceImpl indicativeOfferFourService) {
        this.indicativeOfferOneService = indicativeOfferOneService;
        this.indicativeOfferTwoService = indicativeOfferTwoService;
        this.indicativeOfferThreeService = indicativeOfferThreeService;
        this.indicativeOfferFourService = indicativeOfferFourService;
        this.service = indicativeOfferOneService;
    }

    @Override
    public IndicativeOffer save(IndicativeOffer indicativeOffer) {
        return service.save(indicativeOffer);
    }

    @Override
    public CompanyUser getIndicativeOffer(int number, CompanyUser companyUser) {
        initService(number);
        return service.getIndicativeOffer(companyUser);
    }

    @Override
    public IndicativeOfferTO updateIndicativeOffer(UpdateIndicativeOfferTO to) throws UnprocessableEntityException {
        initService(to.getType());
        return service.updateIndicativeOffer(to);
    }

    @Override
    public IndicativeOffer getById(long id) throws UnprocessableEntityException {
        return service.getById(id);
    }

    private void initService(int type) {
        switch (type) {
            case 1 -> this.service = indicativeOfferOneService;
            case 2 -> this.service = indicativeOfferTwoService;
            case 3 -> this.service = indicativeOfferThreeService;
            default -> this.service = indicativeOfferFourService;
        }
    }

}
