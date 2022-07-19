package com.linkapital.core.services.company.datasource;

import com.linkapital.core.services.company.datasource.domain.CompanyPartners;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyPartnersRepository extends JpaRepository<CompanyPartners, Long> {

}
