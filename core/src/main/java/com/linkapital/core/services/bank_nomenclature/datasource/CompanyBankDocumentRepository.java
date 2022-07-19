package com.linkapital.core.services.bank_nomenclature.datasource;

import com.linkapital.core.services.bank_nomenclature.datasource.domain.CompanyBankDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyBankDocumentRepository extends JpaRepository<CompanyBankDocument, Long> {

}
