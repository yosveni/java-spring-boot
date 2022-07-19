package com.linkapital.core.services.company.impl;

import com.linkapital.core.services.company.CompanyPartnersService;
import com.linkapital.core.services.company.datasource.CompanyPartnersRepository;
import com.linkapital.core.services.company.datasource.domain.CompanyPartners;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CompanyPartnersServiceImpl implements CompanyPartnersService {

    private final CompanyPartnersRepository companyPartnersRepository;

    public CompanyPartnersServiceImpl(CompanyPartnersRepository companyPartnersRepository) {
        this.companyPartnersRepository = companyPartnersRepository;
    }

    @Override
    public List<CompanyPartners> saveAll(List<CompanyPartners> companyPartners) {
        return companyPartnersRepository.saveAll(companyPartners);
    }

    @Override
    public void deleteAll(Set<CompanyPartners> companyPartners) {
        companyPartnersRepository.deleteAll(companyPartners);
    }

}
