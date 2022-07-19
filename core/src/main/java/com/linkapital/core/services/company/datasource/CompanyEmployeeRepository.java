package com.linkapital.core.services.company.datasource;

import com.linkapital.core.services.company.datasource.domain.CompanyEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyEmployeeRepository extends JpaRepository<CompanyEmployee, Long> {

}
