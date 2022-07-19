package com.linkapital.core.services.credit_information.datasource;

import com.linkapital.core.services.credit_information.datasource.domain.CreditInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditInformationRepository extends JpaRepository<CreditInformation, Long> {

    @Query(value = "select tci.consult_date from tab_credit_information tci " +
            "join tab_company tc on tc.id = tci.company_id " +
            "join tab_company_main_info tcmi on tcmi.id = tc.main_info_id " +
            "where tcmi.cnpj = ?1 order by consult_date desc", nativeQuery = true)
    List<String> getConsultDatesByCompanyCnpj(String cnpj);

}
