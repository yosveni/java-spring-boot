package com.linkapital.core.services.offer.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankNomenclatureTO;
import com.linkapital.core.services.company.contract.to.SelectIndicativeOfferTO;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.RequestOfferTO;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.offer.OfferService;
import com.linkapital.core.services.offer.contract.to.AcceptOfferTO;
import com.linkapital.core.services.offer.contract.to.AcceptPartnerOfferTO;
import com.linkapital.core.services.offer.contract.to.CreateOfferLogTO;
import com.linkapital.core.services.offer.contract.to.UpdateOfferStateTO;
import com.linkapital.core.services.offer.contract.to.create.CreateOfferTO;
import com.linkapital.core.services.offer.contract.to.get.OfferTO;
import com.linkapital.core.services.offer.contract.to.update.UpdateOfferTO;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@Primary
public class FacadeOfferServiceImpl implements OfferService {

    private final OfferOneServiceImpl offerOneService;
    private final OfferTwoServiceImpl offerTwoService;
    private final OfferThreeServiceImpl offerThreeService;
    private final OfferFourServiceImpl offerFourService;
    private BaseOfferServiceImpl service;

    public FacadeOfferServiceImpl(OfferOneServiceImpl offerOneService,
                                  OfferTwoServiceImpl offerTwoService,
                                  OfferThreeServiceImpl offerThreeService,
                                  OfferFourServiceImpl offerFourService) {
        this.offerOneService = offerOneService;
        this.offerTwoService = offerTwoService;
        this.offerThreeService = offerThreeService;
        this.offerFourService = offerFourService;
        this.service = offerOneService;
    }

    @Override
    public Offer save(Offer offer) {
        return service.save(offer);
    }

    @Override
    public List<OfferTO> create(List<CreateOfferTO> to) throws UnprocessableEntityException {
        return service.create(to);
    }

    @Override
    public OfferTO createNotification(CreateOfferLogTO to) throws UnprocessableEntityException {
        return service.createNotification(to);
    }

    @Override
    public OfferTO update(UpdateOfferTO to) throws UnprocessableEntityException {
        initService(to.getType());
        return service.update(to);
    }

    @Override
    public OfferTO acceptPartnerOffer(AcceptPartnerOfferTO to) throws UnprocessableEntityException {
        return service.acceptPartnerOffer(to);
    }

    @Override
    public OfferTO updateState(UpdateOfferStateTO to) throws UnprocessableEntityException {
        return service.updateState(to);
    }

    @Override
    public OfferTO uploadOfferContract(long offerId,
                                       Date contractDate,
                                       MultipartFile[] files) throws UnprocessableEntityException {
        return service.uploadOfferContract(offerId, contractDate, files);
    }

    @Override
    public CompanyClientTO selectIndicativeOffer(SelectIndicativeOfferTO to) throws UnprocessableEntityException {
        return service.selectIndicativeOffer(to);
    }

    @Override
    public CompanyClientTO requestOffer(RequestOfferTO to) throws UnprocessableEntityException {
        return service.requestOffer(to);
    }

    @Override
    public CompanyClientTO acceptOffer(AcceptOfferTO to) throws UnprocessableEntityException {
        return service.acceptOffer(to);
    }

    @Override
    public Offer getById(Long id) throws UnprocessableEntityException {
        return service.getById(id);
    }

    @Override
    public List<OfferTO> getAll() {
        return service.getAll();
    }

    @Override
    public List<OfferTO> getAllByCnpj(String cnpj) {
        return service.getAllByCnpj(cnpj);
    }

    @Override
    public List<Offer> getAllByUserIdAndCommissionIsNotNull(long userId) {
        return service.getAllByUserIdAndCommissionIsNotNull(userId);
    }

    @Override
    public List<DirectoryTO> getDocumentsForCommentsOffer(long id) throws UnprocessableEntityException {
        return service.getDocumentsForCommentsOffer(id);
    }

    @Override
    public List<CompanyBankNomenclatureTO> getAllNomenclaturesById(long id) throws UnprocessableEntityException {
        return service.getAllNomenclaturesById(id);
    }

    @Override
    public void delete(Long id) throws UnprocessableEntityException {
        service.delete(id);
    }

    private void initService(int type) {
        switch (type) {
            case 1 -> this.service = this.offerOneService;
            case 2 -> this.service = this.offerTwoService;
            case 3 -> this.service = this.offerThreeService;
            default -> this.service = this.offerFourService;
        }
    }

}
