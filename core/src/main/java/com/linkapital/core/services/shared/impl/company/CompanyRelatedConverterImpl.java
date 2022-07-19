package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.shared.CompanyConverter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static com.linkapital.core.services.shared.contract.CompanyRelatedBinder.build;
import static java.util.stream.Collectors.toSet;

@Service
public class CompanyRelatedConverterImpl implements CompanyConverter {

    //Level 1
    private static final String companiesRelated = "empresasColigadas";

    private final CompanyService companyService;

    public CompanyRelatedConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        companyService.addConverterDependent(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) throws ParseException {
        if (data.get(companiesRelated) != null) {
            var companiesRelatedLevel1 = (List<Map>) data.get(companiesRelated);
            var related = company.getCompaniesRelated();

            if (!related.isEmpty())
                related.clear();

            related.addAll(companiesRelatedLevel1
                    .stream()
                    .map(build)
                    .collect(toSet()));
        }

        return company;
    }

}
