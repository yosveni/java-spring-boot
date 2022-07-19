package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.CompanyMainInfo;
import com.linkapital.core.services.shared.CompanyConverter;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.linkapital.core.services.shared.contract.CompanyAffiliateBinder.bindCompanyMainInfo;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.StringUtils.hasText;

@Service
public class CompanyAffiliateConverterImpl implements CompanyConverter {

    //Level 1
    private final static String KEY_MATRIX = "matriz";
    private final static String KEY_FILIAL = "filiais";
    private final static String KEY_ENTERPRISE = "empresa";
    //Level 2
    private final static String cnpj = "cnpj";

    private final CompanyService companyService;

    public CompanyAffiliateConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        this.companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) {
        if (data.get(KEY_MATRIX) != null)
            company.setMatrixInfo(getCompanyMainInfo((Map) data.get(KEY_MATRIX)));

        if (data.get(KEY_FILIAL) != null) {
            var affiliatesLevel1 = (List<Map>) data.get(KEY_FILIAL);
            company.getAffiliates().clear();
            company.getAffiliates().addAll(affiliatesLevel1
                    .stream()
                    .map(this::getCompanyMainInfo)
                    .filter(Objects::nonNull)
                    .collect(toSet()));
        }

        var enterpriseLevel1 = (Map) data.get(KEY_ENTERPRISE);
        company.setMatrix(enterpriseLevel1 != null &&
                enterpriseLevel1.get(KEY_MATRIX) != null &&
                (boolean) enterpriseLevel1.get(KEY_MATRIX));

        return company;
    }

    private @Nullable CompanyMainInfo getCompanyMainInfo(@NonNull Map map) {
        var cnpjLevel2 = map.get(cnpj) == null
                ? null
                : map.get(cnpj).toString();

        if (!hasText(cnpjLevel2))
            return null;

        var atomicReference = new AtomicReference<CompanyMainInfo>();
        Optional
                .ofNullable(companyService.getByCnpjAux(cnpjLevel2))
                .ifPresentOrElse(company -> atomicReference.set(company.getMainInfo()),
                        () -> {
                            var company = companyService.save(new Company()
                                    .withMainInfo(bindCompanyMainInfo.apply(map)));
                            atomicReference.set(company.getMainInfo());
                        });

        return atomicReference.get();
    }

}
