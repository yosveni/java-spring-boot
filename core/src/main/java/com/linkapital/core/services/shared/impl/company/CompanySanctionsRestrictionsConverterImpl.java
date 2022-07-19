package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Ceis;
import com.linkapital.core.services.company.datasource.domain.Cepim;
import com.linkapital.core.services.company.datasource.domain.Cnep;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.InternationalList;
import com.linkapital.core.services.shared.CompanyConverter;
import com.linkapital.core.util.DateUtil;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Service
public class CompanySanctionsRestrictionsConverterImpl implements CompanyConverter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //Level 1
    private final String companyCeis = "empresaCeis";
    private final String companyCepim = "empresaCepim";
    private final String companyCnep = "cnep";
    private final String internationalList = "listaInternacional";
    //Level 2
    private final String sancoes = "sancoes";
    private final String convenios = "convenios";
    private final String processos = "processos";
    private final String nameInternationalList = "nome";
    private final String internationalListDate = "dataProcessamento";
    //Level 3
    private final String periodoSancao = "periodoSancao";
    private final String organComplementCeis = "complementoOrgao";
    private final String informationDateCeis = "dataInformacao";
    private final String legalSubstantiationCeis = "descricaoFundamentacaoLegal";
    private final String processNumberCeis = "processo";
    private final String sanctioningEntity = "orgaoSancionador";
    private final String informationEntityCeis = "origemInformacao";
    private final String sanction = "tipoSancao";
    private final String ufSanctioningEntityCeis = "uf";
    private final String grantorEntityCepim = "concedente";
    private final String endContractDateCepim = "fimVigencia";
    private final String impedimentCepim = "impedimento";
    private final String contractCepim = "convenio";
    private final String valueReleasedCepim = "valorLiberado";
    private final String initSanctionDateCnep = "dataInicioSancao";
    private final String endSanctionDateCnep = "dataFinalSancao";
    private final String processNumberCnep = "numeroProcesso";
    private final String ufSanctioningEntityCnep = "ufOrgaoSancionador";
    private final String penaltyValueCnep = "valorMulta";
    //Level 4
    private final String endSanctionDateCeis = "final";
    private final String initSanctionDateCeis = "inicio";

    private final CompanyService companyService;

    public CompanySanctionsRestrictionsConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) throws ParseException {
        var companyCeisLevel1 = data.get(companyCeis) == null
                ? null
                : (List<Map>) ((Map) data.get(companyCeis)).get(sancoes);

        if (companyCeisLevel1 != null) {
            var ceisList = company.getCeis();

            if (!ceisList.isEmpty())
                ceisList.clear();

            ceisList.addAll(companyCeisLevel1
                    .stream()
                    .map(map -> {
                        var ceis = new Ceis();

                        ceis.setOrganComplement(nonNull(map.get(organComplementCeis))
                                ? map.get(organComplementCeis).toString()
                                : null);
                        ceis.setLegalSubstantiation(nonNull(map.get(legalSubstantiationCeis))
                                ? map.get(legalSubstantiationCeis).toString()
                                : null);
                        ceis.setProcessNumber(nonNull(map.get(processNumberCeis))
                                ? map.get(processNumberCeis).toString()
                                : null);
                        ceis.setSanctioningEntity(nonNull(map.get(sanctioningEntity))
                                ? map.get(sanctioningEntity).toString()
                                : null);
                        ceis.setInformationEntity(nonNull(map.get(informationEntityCeis))
                                ? map.get(informationEntityCeis).toString()
                                : null);
                        ceis.setSanction(nonNull(map.get(sanction))
                                ? map.get(sanction).toString()
                                : null);
                        ceis.setUfSanctioningEntity(nonNull(map.get(ufSanctioningEntityCeis))
                                ? map.get(ufSanctioningEntityCeis).toString()
                                : null);

                        try {
                            if (nonNull(map.get(periodoSancao))) {
                                var periodoSancaoLevel3 = (Map) map.get(periodoSancao);

                                ceis.setEndSanctionDate(nonNull(periodoSancaoLevel3.get(endSanctionDateCeis))
                                        ? DateUtil.convert(periodoSancaoLevel3.get(endSanctionDateCeis).toString())
                                        : null);
                                ceis.setInitSanctionDate(nonNull(periodoSancaoLevel3.get(initSanctionDateCeis))
                                        ? DateUtil.convert(periodoSancaoLevel3.get(initSanctionDateCeis).toString())
                                        : null);
                            }
                            ceis.setInformationDate(nonNull(map.get(informationDateCeis))
                                    ? DateUtil.convert(map.get(informationDateCeis).toString())
                                    : null);
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        }

                        return ceis;
                    })
                    .collect(toSet()));
        }

        var companyCepimLevel1 = nonNull(data.get(companyCepim))
                ? (List<Map>) ((Map) data.get(companyCepim)).get(convenios)
                : null;
        if (nonNull(companyCepimLevel1)) {
            var cepimsList = company.getCepims();

            if (!cepimsList.isEmpty())
                cepimsList.clear();

            cepimsList.addAll(companyCepimLevel1
                    .stream()
                    .map(map -> {
                        var cepim = new Cepim();

                        cepim.setGrantorEntity(nonNull(map.get(grantorEntityCepim))
                                ? map.get(grantorEntityCepim).toString()
                                : null);
                        cepim.setImpediment(nonNull(map.get(impedimentCepim))
                                ? map.get(impedimentCepim).toString()
                                : null);
                        cepim.setContract(nonNull(map.get(contractCepim))
                                ? map.get(contractCepim).toString()
                                : null);
                        cepim.setValueReleased(nonNull(map.get(valueReleasedCepim))
                                ? Double.parseDouble(map.get(valueReleasedCepim).toString())
                                : 0);

                        try {
                            cepim.setEndContractDate(nonNull(map.get(endContractDateCepim))
                                    ? DateUtil.convert(map.get(endContractDateCepim).toString())
                                    : null);
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        }

                        return cepim;
                    })
                    .collect(toSet()));
        }

        var companyCnepLevel1 = nonNull(data.get(companyCnep))
                ? (List<Map>) ((Map) data.get(companyCnep)).get(processos)
                : null;
        if (nonNull(companyCnepLevel1)) {
            var cnepsList = company.getCneps();

            if (!cnepsList.isEmpty())
                cnepsList.clear();

            cnepsList.addAll(companyCnepLevel1
                    .stream()
                    .map(map -> {
                        var cnep = new Cnep();

                        cnep.setProcessNumber(nonNull(map.get(processNumberCnep))
                                ? map.get(processNumberCnep).toString()
                                : null);
                        cnep.setSanctioningEntity(nonNull(map.get(sanctioningEntity))
                                ? map.get(sanctioningEntity).toString()
                                : null);
                        cnep.setSanction(nonNull(map.get(sanction))
                                ? map.get(sanction).toString()
                                : null);
                        cnep.setUfSanctioningEntity(nonNull(map.get(ufSanctioningEntityCnep))
                                ? map.get(ufSanctioningEntityCnep).toString()
                                : null);
                        cnep.setPenaltyValue(nonNull(map.get(penaltyValueCnep))
                                ? Double.parseDouble(map.get(penaltyValueCnep).toString())
                                : 0);

                        try {
                            cnep.setEndSanctionDate(nonNull(map.get(endSanctionDateCnep))
                                    ? DateUtil.convert(map.get(endSanctionDateCnep).toString())
                                    : null);
                            cnep.setInitSanctionDate(nonNull(map.get(initSanctionDateCnep))
                                    ? DateUtil.convert(map.get(initSanctionDateCnep).toString())
                                    : null);
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        }

                        return cnep;
                    })
                    .collect(toSet()));
        }

        if (nonNull(data.get(internationalList))) {
            var internationalListLevel1 = (List<Map>) data.get(internationalList);
            var international = company.getInternationalLists();

            if (!international.isEmpty())
                international.clear();

            international.addAll(internationalListLevel1
                    .stream()
                    .map(map -> {
                        var internationalList = new InternationalList();
                        internationalList.setName(nonNull(map.get(nameInternationalList))
                                ? map.get(nameInternationalList).toString()
                                : null);

                        try {
                            internationalList.setQueryDate(nonNull(map.get(internationalListDate))
                                    ? DateUtil.convert(map.get(internationalListDate).toString())
                                    : null);
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        }

                        return internationalList;
                    })
                    .collect(toSet()));
        }

        return company;
    }

}
