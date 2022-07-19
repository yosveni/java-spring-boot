package com.linkapital.core.services.invoice;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning3TO;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import org.springframework.web.multipart.MultipartFile;

/**
 * default interface for {@link InvoiceService}
 * Service with the responsibility of carrying out analysis operations of the company's invoices
 *
 * @since 0.0.1
 */
public interface InvoiceService {

    /**
     * Perform analysis of the invoices issued and received
     *
     * @param companyUser {@link CompanyUser} the company to update
     * @return {@link CompanyLearning3TO} the cnpj, horizontal and vertical analysis of invoices
     */
    CompanyLearning3TO buildAnalysis(CompanyUser companyUser);

    /**
     * Parse the invoices issued and received
     *
     * @param clean        {@link Boolean} Indicates whether existing invoices should be deleted
     * @param companyUser  {@link CompanyUser} the company to update
     * @param invoicesFile {@link MultipartFile} the invoices file
     * @throws UnprocessableEntityException if an error occurs while the invoice files are being read
     */
    void parseInvoices(boolean clean, CompanyUser companyUser, MultipartFile invoicesFile) throws UnprocessableEntityException;

}
