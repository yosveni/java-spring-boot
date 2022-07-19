package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.EnvironmentalLicense;
import com.linkapital.core.services.company.datasource.domain.HealthEstablishment;
import com.linkapital.core.services.shared.CompanyConverter;
import com.linkapital.core.util.DateUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Service
public class CompanyEnvironmentalConverterImpl implements CompanyConverter {

    //Level 1
    private final String environmentalLicenses = "licencasAmbientais";
    private final String covid19 = "datascienceRiscoMercadoCovid19";
    private final String empresaDatasusEstabelecimento = "empresaDatasusEstabelecimento";
    //Level 2
    private final String updateData = "dataAtualizacao";
    private final String emitData = "dataEmissao";
    private final String descriptionTypology = "descricaoTipologia";
    private final String municipality = "municipio";
    private final String processNumber = "numProcesso";
    private final String typologyNumber = "numeroTipologia";
    private final String typeLicense = "tipo";
    private final String ufLicense = "uf";
    private final String situation = "situacao";
    private final String covid19Individual = "riscoIndividual";
    private final String covid19Segment = "riscoSegmento";
    private final String healthEstablishments = "estabelecimentosSaude";
    //Level 3
    private final String quantityBeds = "quantidadeLeitos";
    private final String quantityProfessionals = "quantidadeProfissionais";
    private final String unitType = "tipoUnidade";
    private final String lastUpdate = "ultimaAtualizacao";

    private final CompanyService companyService;

    public CompanyEnvironmentalConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) throws ParseException {
        if (data.get(environmentalLicenses) != null) {
            var environmentalLicensesLevel1 = (List<Map>) data.get(environmentalLicenses);
            company.getEnvironmentalLicenses().clear();
            company.getEnvironmentalLicenses().addAll(environmentalLicensesLevel1
                    .stream()
                    .map(map -> {
                        var environmentalLicense = new EnvironmentalLicense();
                        environmentalLicense.setDescriptionTypology(nonNull(map.get(descriptionTypology))
                                ? map.get(descriptionTypology).toString()
                                : null);
                        environmentalLicense.setProcessNumber(nonNull(map.get(processNumber))
                                ? map.get(processNumber).toString()
                                : null);
                        environmentalLicense.setMunicipality(nonNull(map.get(municipality))
                                ? map.get(municipality).toString()
                                : null);
                        environmentalLicense.setTypologyNumber(nonNull(map.get(typologyNumber))
                                ? map.get(typologyNumber).toString()
                                : null);
                        environmentalLicense.setSituation(nonNull(map.get(situation))
                                ? map.get(situation).toString()
                                : null);
                        environmentalLicense.setType(nonNull(map.get(typeLicense))
                                ? map.get(typeLicense).toString()
                                : null);
                        environmentalLicense.setUf(nonNull(map.get(ufLicense))
                                ? map.get(ufLicense).toString()
                                : null);

                        try {
                            environmentalLicense.setEmitData(nonNull(map.get(emitData))
                                    ? DateUtil.convert(map.get(emitData).toString())
                                    : null);
                            environmentalLicense.setUpdateData(nonNull(map.get(updateData))
                                    ? DateUtil.convert(map.get(updateData).toString())
                                    : null);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        return environmentalLicense;
                    })
                    .collect(toSet()));
        }

        if (data.get(covid19) != null) {
            var covid19Level1 = (Map) data.get(covid19);

            company.setCovid19Individual(nonNull(covid19Level1.get(covid19Individual))
                    ? covid19Level1.get(covid19Individual).toString()
                    : null);
            company.setCovid19Segment(nonNull(covid19Level1.get(covid19Segment))
                    ? covid19Level1.get(covid19Segment).toString()
                    : null);
        }

        var healthEstablishmentsLevel2 = nonNull(data.get(empresaDatasusEstabelecimento))
                ? (List<Map>) ((Map) data.get(empresaDatasusEstabelecimento)).get(healthEstablishments)
                : null;
        if (healthEstablishmentsLevel2 != null) {
            company.getHealthEstablishments().clear();
            company.getHealthEstablishments().addAll(healthEstablishmentsLevel2
                    .stream()
                    .map(map -> {
                        var healthEstablishment = new HealthEstablishment();
                        healthEstablishment.setQuantityBeds(nonNull(map.get(quantityBeds))
                                ? parseInt(map.get(quantityBeds).toString())
                                : 0);
                        healthEstablishment.setQuantityProfessionals(nonNull(map.get(quantityProfessionals))
                                ? parseInt(map.get(quantityProfessionals).toString())
                                : 0);
                        healthEstablishment.setUnitType(nonNull(map.get(unitType))
                                ? map.get(unitType).toString()
                                : null);

                        try {
                            healthEstablishment.setLastUpdate(nonNull(map.get(lastUpdate))
                                    ? DateUtil.convert(map.get(lastUpdate).toString())
                                    : null);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        return healthEstablishment;
                    })
                    .collect(toSet()));
        }

        return company;
    }

}
