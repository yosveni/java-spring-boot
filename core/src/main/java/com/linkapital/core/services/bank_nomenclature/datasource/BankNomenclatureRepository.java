package com.linkapital.core.services.bank_nomenclature.datasource;

import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankNomenclatureRepository extends JpaRepository<BankNomenclature, Long> {

    List<BankNomenclature> findAllByIdIn(List<Long> ids);

    @Query(value = "select *\n" +
            "from tab_bank_nomenclature\n" +
            "         join tab_bank_nomenclature_partners_bank tbnpb on tab_bank_nomenclature.id = tbnpb.bank_nomenclature_id\n" +
            "         join tab_partner_bank tpb on tpb.id = tbnpb.partner_bank_id where tpb.name = 'LINKAPITAL'", nativeQuery = true)
    List<BankNomenclature> findAllByPartnerBankLinkapital();

}
