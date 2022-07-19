package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.CnjCnia;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.Crsfn;
import com.linkapital.core.services.company.datasource.domain.WorkMte;
import com.linkapital.core.services.shared.CompanyConverter;
import com.linkapital.core.services.shared.datasource.domain.Address;
import com.linkapital.core.util.DateUtil;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static com.linkapital.core.services.shared.contract.JudicialProcessBinder.JUDICIAL_PROCESS_BINDER;
import static com.linkapital.core.services.shared.contract.ProconBinder.PROCON_BINDER;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Service
public class CompanyLegalConverterImpl implements CompanyConverter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //level 1
    private final String procon = "proconInformacoes";
    private final String judicialProcess = "processoJudicialTotalizadores";
    private final String mteTrabalhoEscravo = "mteTrabalhoEscravo";
    private final String cnjCnia = "cnjCnia";
    private final String bancoCentral = "bancoCentral";
    //level 2
    private final String workMtes = "estabelecimentos";
    private final String process = "processos";
    private final String crsfn = "acordaos";
    //level 3
    private final String fiscalActionYear = "anoAcaoFiscal";
    private final String provenanceDecisionDate = "dataDecisaoProcedencia";
    private final String address = "estabelecimento";
    private final String quantityWorkers = "numeroTrabalhadoresEnvolvidos";
    private final String relatedIssues = "assuntosRelacionados";
    private final String cargoFuncao = "cargoFuncao";
    private final String registrationDate = "dataCadastramento";
    private final String descriptionEntity = "descricaoOrgao";
    private final String sphere = "esfera";
    private final String ressarcimentoIntegralDano = "ressarcimentoIntegralDano";
    private final String agreedNumber = "numeroAcordaoCRSFN";
    private final String resourceNumber = "numeroRecurso";
    private final String part = "parte";
    private final String resourceType = "recurso";
    //level 4
    private final String processNumber = "numeroProcesso";
    private final String address2 = "complemento";
    private final String neighborhood = "logradouro";
    private final String municipality = "municipio";
    private final String uf = "uf";
    private final String value = "valor";
    private final CompanyService companyService;

    public CompanyLegalConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        this.companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) throws ParseException {
        if (nonNull(data.get(procon)))
            company.setProcon(PROCON_BINDER.binder((Map) data.get(procon)));

        if (nonNull(data.get(judicialProcess)))
            company.setJudicialProcess(JUDICIAL_PROCESS_BINDER.bind((Map) data.get(judicialProcess)));

        var workMtesLevel2 = nonNull(data.get(mteTrabalhoEscravo))
                ? (List<Map>) ((Map) data.get(mteTrabalhoEscravo)).get(workMtes)
                : null;
        if (nonNull(workMtesLevel2)) {
            var workMtes = company.getWorkMtes();

            if (!workMtes.isEmpty())
                workMtes.clear();

            workMtes.addAll(workMtesLevel2
                    .stream()
                    .map(map -> {
                        var workMte = new WorkMte();
                        workMte.setFiscalActionYear(nonNull(map.get(fiscalActionYear))
                                ? parseInt(map.get(fiscalActionYear).toString())
                                : 0);
                        workMte.setQuantityWorkers(nonNull(map.get(quantityWorkers))
                                ? parseInt(map.get(quantityWorkers).toString())
                                : 0);
                        workMte.setAddress(getAddress(map));

                        try {
                            workMte.setProvenanceDecisionDate(nonNull(map.get(provenanceDecisionDate))
                                    ? DateUtil.convert(map.get(provenanceDecisionDate).toString())
                                    : null);
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        }

                        return workMte;
                    })
                    .collect(toSet()));
        }

        var cnjCniaLevel1 = nonNull(data.get(cnjCnia))
                ? (List<Map>) ((Map) data.get(cnjCnia)).get(process)
                : null;
        if (nonNull(cnjCniaLevel1)) {
            var cnjCniasList = company.getCnjCnias();

            if (!cnjCniasList.isEmpty())
                cnjCniasList.clear();

            cnjCniasList.addAll(cnjCniaLevel1
                    .stream()
                    .map(map -> {
                        var cnjCnia = new CnjCnia();
                        cnjCnia.setProcessNumber(nonNull(map.get(processNumber))
                                ? map.get(processNumber).toString()
                                : null);
                        cnjCnia.setSphere(nonNull(map.get(sphere))
                                ? map.get(sphere).toString()
                                : null);
                        cnjCnia.setDescriptionEntity(nonNull(map.get(descriptionEntity))
                                ? map.get(descriptionEntity).toString()
                                : null);
                        cnjCnia.setRelatedIssues(nonNull(map.get(relatedIssues))
                                ? (List<String>) map.get(relatedIssues)
                                : null);

                        try {
                            cnjCnia.setRegistrationDate(nonNull(map.get(registrationDate))
                                    ? DateUtil.convert(map.get(registrationDate).toString())
                                    : null);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (nonNull(map.get(cargoFuncao))) {
                            var map1 = (Map) map.get(cargoFuncao);
                            cnjCnia.setUf(nonNull(map1.get(uf))
                                    ? map1.get(uf).toString()
                                    : null);
                        }

                        if (nonNull(map.get(ressarcimentoIntegralDano))) {
                            var map1 = (Map) map.get(ressarcimentoIntegralDano);
                            cnjCnia.setValue(nonNull(map1.get(value))
                                    ? parseDouble(map1.get(value).toString())
                                    : 0);
                        }

                        return cnjCnia;
                    })
                    .collect(toSet()));
        }

        var crsfnLevel2 = nonNull(data.get(bancoCentral))
                ? (List<Map>) ((Map) data.get(bancoCentral)).get(crsfn)
                : null;
        if (nonNull(crsfnLevel2)) {
            var crsfnsList = company.getCrsfns();

            if (!crsfnsList.isEmpty())
                crsfnsList.clear();

            crsfnsList.addAll(crsfnLevel2
                    .stream()
                    .map(map -> {
                        var crsfn = new Crsfn();
                        crsfn.setAgreedNumber(nonNull(map.get(agreedNumber))
                                ? map.get(agreedNumber).toString()
                                : null);
                        crsfn.setProcessNumber(nonNull(map.get(processNumber))
                                ? map.get(processNumber).toString()
                                : null);
                        crsfn.setResourceNumber(nonNull(map.get(resourceNumber))
                                ? map.get(resourceNumber).toString()
                                : null);
                        crsfn.setPart(nonNull(map.get(part))
                                ? map.get(part).toString()
                                : null);
                        crsfn.setResourceType(nonNull(map.get(resourceType))
                                ? map.get(resourceType).toString()
                                : null);

                        return crsfn;
                    })
                    .collect(toSet()));
        }

        return company;
    }

    private @Nullable Address getAddress(@NonNull Map data) {
        if (data.get(address) == null)
            return null;

        var addressLevel3 = (Map) data.get(address);
        var addressNew = new Address();
        addressNew.setNeighborhood(nonNull(addressLevel3.get(neighborhood))
                ? addressLevel3.get(neighborhood).toString()
                : null);
        addressNew.setAddress2(nonNull(addressLevel3.get(address2))
                ? addressLevel3.get(address2).toString()
                : null);
        addressNew.setMunicipality(nonNull(addressLevel3.get(municipality))
                ? addressLevel3.get(municipality).toString()
                : null);
        addressNew.setUf(nonNull(addressLevel3.get(uf))
                ? addressLevel3.get(uf).toString()
                : null);

        return addressNew;
    }

}
