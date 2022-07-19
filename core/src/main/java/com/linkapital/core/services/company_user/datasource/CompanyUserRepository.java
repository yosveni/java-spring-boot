package com.linkapital.core.services.company_user.datasource;

import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {

    Optional<CompanyUser> findByCompany_MainInfo_CnpjAndUser_Id(String cnpj, Long id);

    List<CompanyUser> findAllByUser_Id(long id);

}
