package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.CompanyEmployee;
import com.linkapital.core.services.shared.CompanyConverter;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.linkapital.core.services.shared.contract.CompanyBeneficiaryBinder.getCompanyBeneficiaries;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.StringUtils.hasText;

@Service
public class CompanyPersonsConverterImpl implements CompanyConverter {

    //Level 1
    private final String employees = "funcionarios";
    private final String exEmployees = "exfuncionarios";
    //Level 2
    private final String cpf = "cpf";
    private final String admissionDate = "dataAdmissao";
    private final String resignationYear = "anoMesDesligamento";
    private final String birthDate = "dataNascimento";
    private final String name = "nome";

    private final CompanyService companyService;

    public CompanyPersonsConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) {
        if (data.get(employees) != null) {
            company.getEmployees().clear();
            company.getEmployees().addAll(populate((List<Map>) data.get(employees)));
        }

        if (data.get(exEmployees) != null) {
            company.getExEmployees().clear();
            company.getExEmployees().addAll(populate((List<Map>) data.get(exEmployees)));
        }

        company.getBeneficiaries().clear();
        company.getBeneficiaries().addAll(getCompanyBeneficiaries.apply(company.isHasDivergentQSA(), data));

        return company;
    }

    private Set<CompanyEmployee> populate(@NonNull List<Map> source) {
        return source.stream()
                .map(this::getPerson)
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    private @Nullable CompanyEmployee getPerson(@NonNull Map data) {
        var cpfValue = nonNull(data.get(cpf))
                ? data.get(cpf).toString()
                : null;

        if (!hasText(cpfValue))
            return null;

        return new CompanyEmployee()
                .withCpf(cpfValue)
                .withName(data.get(name) == null
                        ? null
                        : data.get(name).toString())
                .withResignationYear(data.get(resignationYear) == null
                        ? null
                        : data.get(resignationYear).toString())
                .withAdmissionDate(data.get(admissionDate) == null
                        ? null
                        : LocalDateTime.parse(data.get(admissionDate).toString(), ISO_ZONED_DATE_TIME))
                .withBirthDate(data.get(birthDate) == null
                        ? null
                        : LocalDateTime.parse(data.get(birthDate).toString(), ISO_ZONED_DATE_TIME));
    }

}
