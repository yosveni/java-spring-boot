package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.FinancialActivity;
import com.linkapital.core.services.shared.CompanyConverter;
import com.linkapital.core.util.DateUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.util.Objects.nonNull;

@Service
public class CompanyBillingConverterImpl implements CompanyConverter {

    //Level 1
    private final String billing = "faturamentoEstimado";
    private final String potentialConsumption = "potencialConsumo";
    private final String financialActivity = "atividadeFinanceira";
    //Level 2
    private final String socialCapital = "valorCapitalSocial";
    private final String estimatedBilling = "faixaIndividual";
    private final String grossBilling = "individual";
    private final String queryDate = "dataConsulta";
    private final String enablementDate = "dataHabilitacao";
    private final String enablementNumber = "numeroHabilitacao";
    private final String segment = "segmentos";
    private final String enablementSituation = "situacaoHabilitacao";

    private final CompanyService companyService;

    public CompanyBillingConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        this.companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) throws ParseException {
        if (nonNull(data.get(billing))) {
            var billingLevel1 = (Map) data.get(billing);
            company.setEstimatedBilling(nonNull(billingLevel1.get(estimatedBilling))
                    ? billingLevel1.get(estimatedBilling).toString()
                    : null);
            company.setGrossBilling(nonNull(billingLevel1.get(grossBilling))
                    ? parseDouble(billingLevel1.get(grossBilling).toString())
                    : 0);
        }

        var potentialConsumptionLevel1 = (Map) data.get(potentialConsumption);
        company.setSocialCapital(nonNull(potentialConsumptionLevel1) &&
                nonNull(potentialConsumptionLevel1.get(socialCapital))
                ? parseDouble(potentialConsumptionLevel1.get(socialCapital).toString())
                : 0);

        if (nonNull(data.get(financialActivity))) {
            var financialActivityLevel1 = (Map) data.get(financialActivity);
            var financialActivity = new FinancialActivity();
            financialActivity.setQueryDate(nonNull(financialActivityLevel1.get(queryDate))
                    ? DateUtil.convert(financialActivityLevel1.get(queryDate).toString())
                    : null);
            financialActivity.setEnablementDate(nonNull(financialActivityLevel1.get(enablementDate))
                    ? DateUtil.convert(financialActivityLevel1.get(enablementDate).toString())
                    : null);
            financialActivity.setEnablementNumber(nonNull(financialActivityLevel1.get(enablementNumber))
                    ? financialActivityLevel1.get(enablementNumber).toString()
                    : null);
            financialActivity.setSegment(nonNull(financialActivityLevel1.get(segment))
                    ? financialActivityLevel1.get(segment).toString()
                    : null);
            financialActivity.setEnablementSituation(nonNull(financialActivityLevel1.get(enablementSituation))
                    ? financialActivityLevel1.get(enablementSituation).toString()
                    : null);
            company.setFinancialActivity(financialActivity);
        }

        return company;
    }

}
