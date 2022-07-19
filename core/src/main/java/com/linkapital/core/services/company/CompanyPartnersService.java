package com.linkapital.core.services.company;

import com.linkapital.core.services.company.datasource.domain.CompanyPartners;

import java.util.List;
import java.util.Set;

public interface CompanyPartnersService {

    List<CompanyPartners> saveAll(List<CompanyPartners> companyPartners);

    void deleteAll(Set<CompanyPartners> companyPartners);

}
