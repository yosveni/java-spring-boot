package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.CompanyCnaeRepository;
import com.linkapital.core.services.company.datasource.domain.Cnae;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.shared.CompanyConverter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.linkapital.core.services.company.contract.enums.CompanySector.getCompanySector;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.StringUtils.hasText;

@Service
public class CompanyCnaeConverterImpl implements CompanyConverter {

    //Level 1
    private final String principalCnae = "cnaePrincipal";
    private final String cnaes = "cnaes";
    //Level 2
    private final String code = "codigo";
    private final String description = "descricao";
    private final String businessActivity = "ramoAtividade";
    private final String sector = "setor";

    private final CompanyCnaeRepository companyCnaeRepository;
    private final CompanyService companyService;

    public CompanyCnaeConverterImpl(CompanyCnaeRepository companyCnaeRepository, CompanyService companyService) {
        this.companyCnaeRepository = companyCnaeRepository;
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        this.companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) {
        if (data.get(principalCnae) != null) {
            var principalCnaeLevel1 = (Map) data.get(principalCnae);
            var codeCnae = principalCnaeLevel1.get(code) == null
                    ? null
                    : principalCnaeLevel1.get(code).toString();

            if (hasText(codeCnae)) {
                var mainCnae = new AtomicReference<Cnae>();
                companyCnaeRepository.findByCode(codeCnae)
                        .ifPresentOrElse(cnae -> mainCnae.set(cnae
                                        .withBusinessActivity(principalCnaeLevel1.get(businessActivity) == null
                                                ? null
                                                : principalCnaeLevel1.get(businessActivity).toString())
                                        .withSector(principalCnaeLevel1.get(sector) == null
                                                ? null
                                                : getCompanySector(principalCnaeLevel1.get(sector).toString()))),
                                () -> mainCnae.set(new Cnae()
                                        .withCode(codeCnae)
                                        .withDescription(principalCnaeLevel1.get(description) == null
                                                ? null
                                                : principalCnaeLevel1.get(description).toString())
                                        .withBusinessActivity(principalCnaeLevel1.get(businessActivity) == null
                                                ? null
                                                : principalCnaeLevel1.get(businessActivity).toString())
                                        .withSector(principalCnaeLevel1.get(sector) == null
                                                ? null
                                                : getCompanySector(principalCnaeLevel1.get(sector).toString()))));
                company.setMainCnae(mainCnae.get());
            }
        }

        if (data.get(cnaes) != null) {
            var cnaesLevel1 = (List<Map>) data.get(cnaes);
            company.getCnaes().clear();
            company.getCnaes().addAll(cnaesLevel1
                    .stream()
                    .map(map -> {
                        var codeMainCnae = company.getMainCnae().getCode();
                        var codeCnae = map.get(code) == null
                                ? null
                                : map.get(code).toString();
                        var descriptionCnae = map.get(description) == null
                                ? null
                                : map.get(description).toString();

                        return hasText(codeCnae) && !codeCnae.equals(codeMainCnae)
                                ? companyCnaeRepository.findByCode(codeCnae)
                                .orElse(new Cnae()
                                        .withCode(codeCnae)
                                        .withDescription(descriptionCnae))
                                : null;
                    })
                    .filter(Objects::nonNull)
                    .collect(toSet()));
        }

        return company;
    }

}
