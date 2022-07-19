package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.shared.CompanyConverter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static com.linkapital.core.services.company.contract.enums.ActivityLevel.getActivityLevel;
import static com.linkapital.core.services.company.contract.enums.CompanyClosingPropensity.getPropensity;
import static com.linkapital.core.services.company.contract.enums.CompanySize.getCompanySize;
import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Service
public class CompanyRegistrationConverterImpl implements CompanyConverter {

    //Level 1
    private final String enterprise = "empresa";
    private final String info = "info";
    private final String activityLevelV2 = "activityLevelV2";
    private final String closingPropensity = "propensaoFechamento";
    private final String deliveryPropensity = "propensaoDelivery";
    private final String eCommercePropensity = "propensaoEcommerce";
    private final String phones = "telefones";
    //Level 2
    private final String activityLevel = "activityLevel";
    private final String legalNature = "naturezaJuridica";
    private final String specialSituation = "situacaoEspecial";
    private final String registrationSituation = "situacaoCadastral";
    private final String age = "idadeEmpresa";
    private final String fantasyName = "nomeFantasia";
    private final String companySize = "porte";
    private final String hasDivergentQSA = "qsaDivergente";
    private final String companyClosingPropensity = "propensaoEmpresa";
    private final String propensity = "propensao";
    private final String rfEmail = "emailRF";
    private final String number = "numero";
    //Level 3
    private final String legalNatureCode = "codigo";
    private final String legalNatureDescription = "descricao";
    private final String dateSpecialSituation = "data";
    private final String specialSituationStatus = "status";
    private final String dateRegistrationSituation = "data";
    private final String registrationSituationReason = "motivo";

    private final CompanyService companyService;

    public CompanyRegistrationConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        this.companyService.addConverter(this);
    }

    @Override
    public Company convert(@NonNull Company company, @NonNull Map data) {
        var enterpriseLevel1 = (Map) data.get(enterprise);
        var infoLevel1 = (Map) data.get(info);
        var dataActivityLevel1 = (Map) data.get(activityLevelV2);

        company.setAge(nonNull(infoLevel1.get(age))
                ? parseInt(infoLevel1.get(age).toString())
                : 0);
        company.setRfEmail(nonNull(infoLevel1.get(rfEmail))
                ? infoLevel1.get(rfEmail).toString()
                : null);
        company.setFantasyName(nonNull(enterpriseLevel1.get(fantasyName))
                ? enterpriseLevel1.get(fantasyName).toString()
                : null);
        company.setHasDivergentQSA(nonNull(infoLevel1.get(hasDivergentQSA)) &&
                (boolean) infoLevel1.get(hasDivergentQSA));
        company.setActivityLevel(nonNull(dataActivityLevel1.get(activityLevel))
                ? getActivityLevel(dataActivityLevel1.get(activityLevel).toString())
                : null);
        company.setCompanySize(nonNull(enterpriseLevel1.get(companySize))
                ? getCompanySize(enterpriseLevel1.get(companySize).toString())
                : null);

        if (nonNull(enterpriseLevel1.get(legalNature))) {
            var legalNatureLevel2 = (Map) enterpriseLevel1.get(legalNature);
            company.setLegalNatureCode(nonNull(legalNatureLevel2.get(legalNatureCode))
                    ? legalNatureLevel2.get(legalNatureCode).toString()
                    : null);
            company.setLegalNatureDescription(nonNull(legalNatureLevel2.get(legalNatureDescription))
                    ? legalNatureLevel2.get(legalNatureDescription).toString()
                    : null);
        }

        if (nonNull(enterpriseLevel1.get(specialSituation))) {
            var specialSituationLevel2 = (Map) enterpriseLevel1.get(specialSituation);
            company.setSpecialSituation(nonNull(specialSituationLevel2.get(specialSituationStatus))
                    ? specialSituationLevel2.get(specialSituationStatus).toString()
                    : null);
            company.setDateSpecialSituation(nonNull(specialSituationLevel2.get(dateSpecialSituation))
                    ? specialSituationLevel2.get(dateSpecialSituation).toString()
                    : null);
        }

        if (nonNull(enterpriseLevel1.get(registrationSituation))) {
            var registrationSituationLevel2 = (Map) enterpriseLevel1.get(registrationSituation);
            company.setDateRegistrationSituation(nonNull(registrationSituationLevel2.get(dateRegistrationSituation))
                    ? registrationSituationLevel2.get(dateRegistrationSituation).toString()
                    : null);
            company.setRegistrationSituationReason(nonNull(registrationSituationLevel2.get(registrationSituationReason))
                    ? registrationSituationLevel2.get(registrationSituationReason).toString()
                    : null);
        }

        if (nonNull(data.get(closingPropensity))) {
            var closingPropensityLevel1 = (Map) data.get(closingPropensity);
            company.setCompanyClosingPropensity(nonNull(closingPropensityLevel1.get(companyClosingPropensity))
                    ? getPropensity(closingPropensityLevel1.get(companyClosingPropensity).toString())
                    : null);
        }

        if (nonNull(data.get(deliveryPropensity))) {
            var deliveryPropensityLevel1 = (Map) data.get(deliveryPropensity);
            company.setDeliveryPropensity(nonNull(deliveryPropensityLevel1.get(propensity))
                    ? deliveryPropensityLevel1.get(propensity).toString()
                    : null);
        }

        if (nonNull(data.get(eCommercePropensity))) {
            var eCommercePropensityLevel1 = (Map) data.get(eCommercePropensity);
            company.setECommercePropensity(nonNull(eCommercePropensityLevel1.get(propensity))
                    ? eCommercePropensityLevel1.get(propensity).toString()
                    : null);
        }

        if (nonNull(data.get(phones))) {
            var phonesLevel1 = (List<Map>) data.get(phones);

            if (nonNull(phonesLevel1) && !phonesLevel1.isEmpty()) {
                if (!company.getPhones().isEmpty())
                    company.getPhones().clear();

                company.setPhones(phonesLevel1
                        .stream()
                        .map(map -> nonNull(map.get(number))
                                ? map.get(number).toString()
                                : null)
                        .collect(toSet()));
            }

        }

        return company;
    }

}
