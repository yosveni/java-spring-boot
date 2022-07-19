package com.linkapital.core.services.invoice.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.datasource.domain.EmployeeGrowth;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning3TO;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.invoice.InvoiceService;
import com.linkapital.core.services.invoice.contract.to.HorizontalAnalysisTO;
import com.linkapital.core.services.invoice.contract.to.InvoiceEmployeeTO;
import com.linkapital.core.services.invoice.contract.to.InvoiceIssuedTO;
import com.linkapital.core.services.invoice.contract.to.InvoiceTaxTO;
import com.linkapital.core.services.invoice.contract.to.InvoiceVerticalTO;
import com.linkapital.core.services.invoice.contract.to.VerticalAnalysisTO;
import com.linkapital.core.services.invoice.datasource.domain.Invoice;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.comment.contract.CommentBinder.COMMENT_BINDER;
import static com.linkapital.core.services.invoice.contract.InvoiceBinder.initParseInvoice;
import static com.linkapital.core.services.invoice.contract.InvoiceBinder.parseProducts;
import static com.linkapital.core.services.invoice.contract.enums.InvoiceType.ISSUED;
import static com.linkapital.core.util.QueryUtil.QUERY_HORIZONTAL_BILLING;
import static com.linkapital.core.util.QueryUtil.QUERY_HORIZONTAL_PAYMENT;
import static com.linkapital.core.util.QueryUtil.QUERY_INVOICE_TAX;
import static com.linkapital.core.util.QueryUtil.QUERY_VERTICAL_BILLING;
import static com.linkapital.core.util.QueryUtil.QUERY_VERTICAL_PAYMENT;
import static com.linkapital.core.util.decompress.DecompressUtil.decompress;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toList;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Has the responsibility of managing the invoices's information.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final String PARAM = "param";
    private final Logger log = getLogger(InvoiceServiceImpl.class);
    private final EntityManager entityManager;
    private String CNPJ;
    private List<EmployeeGrowth> employeeGrowths;

    public InvoiceServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CompanyLearning3TO buildAnalysis(CompanyUser companyUser) {
        CNPJ = companyUser.getCompany().getMainInfo().getCnpj();
        this.employeeGrowths = companyUser.getCompany().getEmployeeGrowths()
                .stream()
                .sorted()
                .collect(toList());

        return new CompanyLearning3TO()
                .withCnpj(CNPJ)
                .withAvgReceiptTerm(companyUser.getAvgReceiptTermInvoices())
                .withHorizontalAnalysisTO(calculateHorizontalAnalysis(companyUser.getId()))
                .withVerticalAnalysisTO(calculateVerticalAnalysis(companyUser.getId()))
                .withComments(COMMENT_BINDER.filterComments(companyUser.getComments(), 3));
    }

    @Override
    public void parseInvoices(boolean clean,
                              CompanyUser companyUser,
                              MultipartFile invoicesFile) throws UnprocessableEntityException {
        var list = decompress(invoicesFile);
        if (clean) {
            companyUser.getIssuedInvoices().clear();
            companyUser.getReceivedInvoices().clear();
        }

        if (list.isEmpty())
            throw new UnprocessableEntityException(msg("invoice.no.invoices.of.type.issued.to.perform.analysis"));

        parseXML(list, companyUser);
    }

    //region Enters file content into a list. And add this list to company information.
    private void parseXML(List<MultipartFile> files, CompanyUser companyUser) throws UnprocessableEntityException {
        var invoices = new ArrayList<Invoice>();
        CNPJ = companyUser.getCompany().getMainInfo().getCnpj().substring(0, 8);

        for (MultipartFile file : files) {
            try {
                var document = newInstance()
                        .newDocumentBuilder()
                        .parse(new InputSource(file.getInputStream()));

                if (document == null) continue;

                document.getDocumentElement().normalize();
                var invoice = build(document.getDocumentElement(), CNPJ, invoices);

                if (invoice != null) {
                    if (invoice.getType().equals(ISSUED)) {
                        invoice.setCompanyInvoice(companyUser);
                        companyUser.getIssuedInvoices().add(invoice);
                    } else {
                        invoice.setCompanyReceived(companyUser);
                        companyUser.getReceivedInvoices().add(invoice);
                    }

                    invoices.add(invoice);
                }
            } catch (SAXException | IOException | ParserConfigurationException e) {
                log.error(e.getMessage());
            }
        }

        if (companyUser.getIssuedInvoices().isEmpty())
            throw new UnprocessableEntityException(msg("invoice.no.invoices.of.type.issued.to.perform.analysis"));
    }
    //endregion

    //region Extracts contained information into a file.
    private Invoice build(Element element, String cnpj, List<Invoice> invoices) {
        if (element == null)
            return null;

        Invoice invoice = null;
        try {
            invoice = initParseInvoice.apply(element, cnpj, invoices);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return invoice == null
                ? null
                : parseProducts.apply(element, invoice);
    }
    //endregion

    //region Calculates issued and received invoices.
    private HorizontalAnalysisTO calculateHorizontalAnalysis(long companyUserId) {
        return new HorizontalAnalysisTO()
                .withInvoiceBilling(buildHorizontalBillingAndPayment(companyUserId, 0))
                .withInvoicePayment(buildHorizontalBillingAndPayment(companyUserId, 1))
                .withInvoiceTax(buildInvoiceTax(companyUserId))
                .withInvoiceEmployee(buildEmployeeGrowth());
    }
    //endregion

    //region Calculates vertical analysis.
    private VerticalAnalysisTO calculateVerticalAnalysis(long companyUserId) {
        return new VerticalAnalysisTO()
                .withInvoiceBilling(buildVerticalBillingAndPayment(companyUserId, 0))
                .withInvoicePayment(buildVerticalBillingAndPayment(companyUserId, 1));
    }
    //endregion

    //region Builds bill and payment to horizontal analysis.
    private List<InvoiceIssuedTO> buildHorizontalBillingAndPayment(long companyUserId, int type) {
        var query = entityManager.createNativeQuery(type == 0
                ? QUERY_HORIZONTAL_BILLING
                : QUERY_HORIZONTAL_PAYMENT);

        query.setParameter(PARAM, companyUserId);
        List<Object[]> results = query.getResultList();

        return results
                .stream()
                .map(obj -> new InvoiceIssuedTO()
                        .withMonth(obj[0] == null
                                ? null
                                : obj[0].toString().strip())
                        .withMonthNumber(obj[1] == null
                                ? 0
                                : (int) obj[1])
                        .withYear(obj[2] == null
                                ? 0
                                : (int) obj[2])
                        .withTotal(obj[3] == null
                                ? 0
                                : BigDecimal
                                        .valueOf((double) obj[3])
                                        .setScale(3, HALF_UP)
                                        .doubleValue()))
                .collect(toList());
    }
    //endregion

    //region Builds bill and payment to vertical analysis.
    private List<InvoiceVerticalTO> buildVerticalBillingAndPayment(long companyUserId, int type) {
        var query = entityManager.createNativeQuery(type == 0
                ? QUERY_VERTICAL_BILLING
                : QUERY_VERTICAL_PAYMENT);

        query.setParameter(PARAM, companyUserId);
        List<Object[]> results = query.getResultList();

        return results
                .stream()
                .map(obj -> new InvoiceVerticalTO()
                        .withCnpj(obj[0] == null
                                ? null
                                : obj[0].toString())
                        .withName((obj[1] == null
                                ? null
                                : obj[1].toString()))
                        .withTotal(obj[2] == null
                                ? 0
                                : BigDecimal
                                        .valueOf((double) obj[2])
                                        .setScale(3, HALF_UP)
                                        .doubleValue()))
                .collect(toList());
    }
    //endregion

    //region Builds the invoice tax.
    private List<InvoiceTaxTO> buildInvoiceTax(long companyUserId) {
        var query = entityManager.createNativeQuery(QUERY_INVOICE_TAX);

        query.setParameter(PARAM, companyUserId);
        List<Object[]> results = query.getResultList();

        return results
                .stream()
                .map(obj -> new InvoiceTaxTO()
                        .withMonth(obj[0] == null
                                ? null
                                : obj[0].toString().strip())
                        .withMonthNumber(obj[1] == null
                                ? 0
                                : (int) obj[1])
                        .withYear(obj[2] == null
                                ? 0
                                : (int) obj[2])
                        .withIpi(obj[3] == null
                                ? 0
                                : BigDecimal
                                        .valueOf((double) obj[3])
                                        .setScale(3, HALF_UP)
                                        .doubleValue())
                        .withPis(obj[4] == null
                                ? 0
                                : BigDecimal
                                        .valueOf((double) obj[4])
                                        .setScale(3, HALF_UP)
                                        .doubleValue())
                        .withCofins(obj[5] == null
                                ? 0
                                : BigDecimal
                                        .valueOf((double) obj[5])
                                        .setScale(3, HALF_UP)
                                        .doubleValue()))
                .collect(toList());
    }
    //endregion

    //region Builds employee growth to horizontal analysis.
    private List<InvoiceEmployeeTO> buildEmployeeGrowth() {
        return this.employeeGrowths
                .stream()
                .map(obj -> new InvoiceEmployeeTO()
                        .withYear(obj.getYear())
                        .withGrowth(obj.getGrowth())
                        .withEmployeeGrowth(obj.getEmployeeGrowth()))
                .collect(toList());
    }
    //endregion

}