package com.linkapital.core.services.invoice.datasource;

import com.linkapital.core.services.invoice.datasource.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
