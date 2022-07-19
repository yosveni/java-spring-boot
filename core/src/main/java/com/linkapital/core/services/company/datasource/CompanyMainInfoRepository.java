package com.linkapital.core.services.company.datasource;

import com.linkapital.core.services.company.datasource.domain.CompanyMainInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyMainInfoRepository extends JpaRepository<CompanyMainInfo, Long> {

}
