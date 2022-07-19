package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.MealProvided;
import com.linkapital.core.services.company.datasource.domain.Pat;
import com.linkapital.core.services.company.datasource.domain.PatModality;
import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.shared.CompanyConverter;
import com.linkapital.core.util.DateUtil;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.linkapital.core.services.company.contract.enums.RegistrationSituation.getRegistrationSituation;
import static io.jsonwebtoken.lang.Strings.hasText;
import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;

@Service
public class CompanyPatConverterImpl implements CompanyConverter {

    //level 1
    private final String companyPat = "patCadastro";
    //level 2
    private final String inscription = "numeroInscricao";
    private final String registrationSituation = "situacaoCadastral";
    private final String benefitedEmployees1 = "totalTrabalhadores";
    private final String exerciseDate = "dataExercicio";
    private final String responsible = "responsavel";
    private final String mealProvides = "refeicoesFornecidas";
    private final String modalities = "modalidades";
    //level 3
    private final String cpf = "cpf";
    private final String email = "email";
    private final String name = "nome";
    private final String quantity = "qtdRefeicaoFornecida";
    private final String type = "tipoRefeicaoFornecida";
    private final String providerCnpj = "cnpjPrestadora";
    private final String providerSocialReason = "prestadora";
    private final String mode = "modalidade";
    private final String over5Sm = "trabalhadoresAcima5sm";
    private final String to5Sm = "trabalhadoresAte5sm";
    private final String benefitedEmployees2 = "trabalhadoresBeneficiados";

    private final CompanyService companyService;
    private final PersonService personService;

    public CompanyPatConverterImpl(CompanyService companyService, PersonService personService) {
        this.companyService = companyService;
        this.personService = personService;
    }

    @PostConstruct
    public void init() {
        companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) throws ParseException {
        if (nonNull(data.get(companyPat))) {
            var companyPatLevel1 = (Map) data.get(companyPat);
            var pat = new Pat();

            pat.setInscription(nonNull(companyPatLevel1.get(inscription))
                    ? companyPatLevel1.get(inscription).toString()
                    : null);
            pat.setRegistrationSituation(nonNull(companyPatLevel1.get(registrationSituation))
                    ? getRegistrationSituation(companyPatLevel1.get(registrationSituation).toString())
                    : null);
            pat.setBenefitedEmployees(nonNull(companyPatLevel1.get(benefitedEmployees1))
                    ? parseInt(companyPatLevel1.get(benefitedEmployees1).toString())
                    : 0);
            pat.setExerciseDate(nonNull(companyPatLevel1.get(exerciseDate))
                    ? DateUtil.convert(companyPatLevel1.get(exerciseDate).toString())
                    : null);

            if (nonNull(companyPatLevel1.get(responsible)))
                pat.setResponsible(getPersonResponsible((Map) companyPatLevel1.get(responsible)));

            if (nonNull(companyPatLevel1.get(mealProvides))) {
                var mealProvidesLevel2 = (List<Map>) companyPatLevel1.get(mealProvides);
                pat.getMealProvides().addAll(mealProvidesLevel2
                        .stream()
                        .map(map -> {
                            var mealProvided = new MealProvided();

                            mealProvided.setQuantity(nonNull(map.get(quantity))
                                    ? parseInt(map.get(quantity).toString())
                                    : 0);
                            mealProvided.setType(nonNull(map.get(type))
                                    ? map.get(type).toString()
                                    : null);

                            return mealProvided;
                        })
                        .toList());
            }

            if (nonNull(companyPatLevel1.get(modalities))) {
                var modalitiesLevel2 = (List<Map>) companyPatLevel1.get(modalities);
                pat.getModalities().addAll(modalitiesLevel2
                        .stream()
                        .map(map -> {
                            var patModality = new PatModality();

                            patModality.setMode(nonNull(map.get(mode))
                                    ? map.get(mode).toString()
                                    : null);
                            patModality.setProviderCnpj(nonNull(map.get(providerCnpj))
                                    ? map.get(providerCnpj).toString()
                                    : null);
                            patModality.setProviderSocialReason(nonNull(map.get(providerSocialReason))
                                    ? map.get(providerSocialReason).toString()
                                    : null);
                            patModality.setOver5Sm(nonNull(map.get(over5Sm))
                                    ? parseInt(map.get(over5Sm).toString())
                                    : 0);
                            patModality.setTo5Sm(nonNull(map.get(to5Sm))
                                    ? parseInt(map.get(to5Sm).toString())
                                    : 0);
                            patModality.setBenefitedEmployees(nonNull(map.get(benefitedEmployees2))
                                    ? parseInt(map.get(benefitedEmployees2).toString())
                                    : 0);

                            return patModality;
                        })
                        .toList());
            }
            company.setPat(pat);
        }

        return company;
    }

    private @Nullable Person getPersonResponsible(@NonNull Map map) {
        var cpf = map.get(this.cpf) == null
                ? null
                : map.get(this.cpf).toString();
        if (!hasText(cpf))
            return null;

        var atomicReference = new AtomicReference<Person>();
        Optional
                .ofNullable(personService.getByCpfAux(cpf))
                .ifPresentOrElse(atomicReference::set,
                        () -> atomicReference.set(new Person()
                                .withCpf(cpf)
                                .withEmail(map.get(email) == null
                                        ? null
                                        : map.get(email).toString())
                                .withName(map.get(name) == null
                                        ? null
                                        : map.get(name).toString())));

        return atomicReference.get();
    }

}
