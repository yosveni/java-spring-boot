package com.linkapital.core.services.cri_cra_debenture.impl;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.cri_cra_debenture.CriCraDebentureService;
import com.linkapital.core.services.cri_cra_debenture.contract.to.CreateCriCraDebentureListTO;
import com.linkapital.core.services.cri_cra_debenture.contract.to.CriCraDebentureTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.linkapital.core.services.cri_cra_debenture.contract.CriCraDebentureBinder.CRI_CRA_DEBENTURE_BINDER;

@Service
@Transactional
public class CriCraDebentureServiceImpl implements CriCraDebentureService {

    private final CompanyService companyService;

    public CriCraDebentureServiceImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public List<CriCraDebentureTO> createCriCraDebentures(CreateCriCraDebentureListTO to)
            throws UnprocessableEntityException {
        Company company;
        try {
            company = companyService.getByCnpj(to.getCnpj());
        } catch (ResourceNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        company.getCriCraDebentures().addAll(CRI_CRA_DEBENTURE_BINDER.bind(to.getElements()));
        companyService.save(company);

        return CRI_CRA_DEBENTURE_BINDER.bindToCriCraDebentureTO(company.getCriCraDebentures());
    }

}
