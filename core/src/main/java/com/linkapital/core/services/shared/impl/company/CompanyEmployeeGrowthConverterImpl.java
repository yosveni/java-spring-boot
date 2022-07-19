package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.shared.CompanyConverter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static com.linkapital.core.services.shared.contract.CompanyGrowthBinder.bindCompany;
import static com.linkapital.core.services.shared.contract.CompanyGrowthBinder.bindEmployeeGrowth;

@Service
public class CompanyEmployeeGrowthConverterImpl implements CompanyConverter {

    //Level 1
    private static final String growth = "crescimentoPorAnoRais";
    private static final String employees = "totalFuncionarios";
    private static final String exEmployees = "exfuncionarios";

    private final CompanyService companyService;

    public CompanyEmployeeGrowthConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        this.companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) {
        if (data.get(growth) != null && data.containsKey(exEmployees)) {
            company.getEmployeeGrowths().clear();
            company.getEmployeeGrowths().addAll(bindEmployeeGrowth.apply(data));
        }

        if (data.get(employees) != null && data.containsKey(employees))
            bindCompany.accept(company, data);

        if (data.get(exEmployees) != null && data.containsKey(exEmployees))
            company.setQuantityExEmployee(((List<Map>) data.get(exEmployees)).size());

        return company;
    }

}
