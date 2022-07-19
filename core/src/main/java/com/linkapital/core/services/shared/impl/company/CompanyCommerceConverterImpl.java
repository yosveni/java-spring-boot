package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.CompanyExport;
import com.linkapital.core.services.company.datasource.domain.Contract;
import com.linkapital.core.services.company.datasource.domain.FinancialIndicator;
import com.linkapital.core.services.company.datasource.domain.ForeignCommerce;
import com.linkapital.core.services.company.datasource.domain.ManagementContract;
import com.linkapital.core.services.company.datasource.domain.ModalityContract;
import com.linkapital.core.services.shared.CompanyConverter;
import com.linkapital.core.util.DateUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.linkapital.core.services.company.contract.enums.EnabledSituation.getEnabledSituation;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Service
public class CompanyCommerceConverterImpl implements CompanyConverter {

    //Level 1
    private final String imports = "importacao";
    private final String exports = "exportacao";
    private final String foreignCommerce = "comercioExterior";
    private final String companyContract = "empresaTransparenciaContratos";
    private final String financialBalance = "empresaBalancoFinanceiro";
    //Level 2
    private final String value = "valor";
    private final String year = "ano";
    private final String situationDate = "dataSituacao";
    private final String modality = "modalidade";
    private final String submodality = "submodalidade";
    private final String enabled = "habilitado";
    private final String enabledSituation = "situacaoHabilitacao";
    private final String authorizedOperations = "operacoesAutorizadas";
    private final String contracts = "contratos";
    private final String contractModality = "modalidadesContratacao";
    private final String quantityContract = "quantidadeContratos";
    private final String totalValue = "valorTotal";
    private final String financialIndicator = "indicadoresFinanceiros";
    //Level 3
    private final String endDate = "dataFimVigencia";
    private final String initDate = "dataInicioVigencia";
    private final String sphere = "esfera";
    private final String contractNumber = "numeroContrato";
    private final String organ = "orgao";
    private final String uf = "ufMunicipioOrigem";
    private final String finalValue = "valorFinal";
    private final String monthsValidity = "vigenciaMeses";
    private final String quantityModality = "quantidade";
    private final String type = "tipo";
    private final String margin = "margemEBIT";
    private final String increase = "crescimentoDaReceita";

    private final CompanyService companyService;

    public CompanyCommerceConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) throws ParseException {
        if (nonNull(data.get(imports))) {
            var importsLevel1 = (List<Map>) data.get(imports);
            var imports = company.getImports();

            if (!imports.isEmpty())
                imports.clear();

            imports.addAll(getData(false, company, importsLevel1));
        }

        if (nonNull(data.get(exports))) {
            var exportsLevel1 = (List<Map>) data.get(exports);
            var exports = company.getExports();

            if (!exports.isEmpty())
                exports.clear();

            exports.addAll(getData(true, company, exportsLevel1));
        }

        if (nonNull(data.get(foreignCommerce))) {
            var foreignCommerceLevel1 = (Map) data.get(foreignCommerce);
            var foreignCommerce = new ForeignCommerce();

            foreignCommerce.setSituationDate(nonNull(foreignCommerceLevel1.get(situationDate))
                    ? DateUtil.convert(foreignCommerceLevel1.get(situationDate).toString())
                    : null);
            foreignCommerce.setModality(nonNull(foreignCommerceLevel1.get(modality))
                    ? foreignCommerceLevel1.get(modality).toString()
                    : null);
            foreignCommerce.setSubmodality(nonNull(foreignCommerceLevel1.get(submodality))
                    ? foreignCommerceLevel1.get(submodality).toString()
                    : null);
            foreignCommerce.setEnabled(nonNull(foreignCommerceLevel1.get(enabled)) &&
                    (boolean) foreignCommerceLevel1.get(enabled));
            foreignCommerce.setEnabledSituation(nonNull(foreignCommerceLevel1.get(enabledSituation))
                    ? getEnabledSituation(foreignCommerceLevel1.get(enabledSituation).toString())
                    : null);
            foreignCommerce.setAuthorizedOperations(nonNull(foreignCommerceLevel1.get(authorizedOperations))
                    ? foreignCommerceLevel1.get(authorizedOperations).toString()
                    : null);

            company.setForeignCommerce(foreignCommerce);
        }

        if (nonNull(data.get(companyContract))) {
            var companyContractLevel1 = (Map) data.get(companyContract);
            var managementContract = new ManagementContract();

            managementContract.setQuantity(nonNull(companyContractLevel1.get(quantityContract))
                    ? parseInt(companyContractLevel1.get(quantityContract).toString())
                    : 0);
            managementContract.setTotalValue(nonNull(companyContractLevel1.get(totalValue))
                    ? parseDouble(companyContractLevel1.get(totalValue).toString())
                    : 0);

            if (nonNull(companyContractLevel1.get(contracts))) {
                var contractsLevel2 = (List<Map>) companyContractLevel1.get(contracts);
                managementContract.getContracts().addAll(contractsLevel2
                        .stream()
                        .map(map -> {
                            var contract = new Contract();
                            contract.setSphere(nonNull(map.get(sphere))
                                    ? map.get(sphere).toString()
                                    : null);
                            contract.setContractNumber(nonNull(map.get(contractNumber))
                                    ? map.get(contractNumber).toString()
                                    : null);
                            contract.setOrgan(nonNull(map.get(organ))
                                    ? map.get(organ).toString()
                                    : null);
                            contract.setUf(nonNull(map.get(uf))
                                    ? map.get(uf).toString()
                                    : null);
                            contract.setFinalValue(nonNull(map.get(finalValue))
                                    ? parseDouble(map.get(finalValue).toString())
                                    : 0);
                            contract.setMonthsValidity(nonNull(map.get(monthsValidity))
                                    ? parseInt(map.get(monthsValidity).toString())
                                    : 0);

                            try {
                                contract.setInitDate(nonNull(map.get(initDate))
                                        ? DateUtil.convert(map.get(initDate).toString())
                                        : null);
                                contract.setEndDate(nonNull(map.get(endDate))
                                        ? DateUtil.convert(map.get(endDate).toString())
                                        : null);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            return contract;
                        })
                        .collect(toSet()));
            }

            if (nonNull(companyContractLevel1.get(contractModality))) {
                var contractModalityLevel2 = (List<Map>) companyContractLevel1.get(contractModality);
                managementContract.getModalityContracts().addAll(contractModalityLevel2
                        .stream()
                        .map(map -> {
                            var modalityContract = new ModalityContract();
                            modalityContract.setType(nonNull(map.get(type))
                                    ? map.get(type).toString()
                                    : null);
                            modalityContract.setQuantity(nonNull(map.get(quantityModality))
                                    ? parseInt(map.get(quantityModality).toString())
                                    : 0);

                            return modalityContract;
                        })
                        .toList());
            }

            company.setManagementContract(managementContract);
        }

        var financialIndicatorLevel2 = nonNull(data.get(financialBalance))
                ? (List<Map>) ((Map) data.get(financialBalance)).get(financialIndicator)
                : null;
        if (nonNull(financialIndicatorLevel2)) {
            var financialIndicators = company.getFinancialIndicators();

            if (!financialIndicators.isEmpty())
                financialIndicators.clear();

            financialIndicators.addAll(financialIndicatorLevel2
                    .stream()
                    .map(map -> {
                        var financialIndicator = new FinancialIndicator();
                        financialIndicator.setMargin(nonNull(map.get(margin))
                                ? parseDouble(map.get(margin).toString())
                                : 0);
                        financialIndicator.setIncrease(nonNull(map.get(increase))
                                ? parseDouble(map.get(increase).toString())
                                : 0);

                        return financialIndicator;
                    })
                    .collect(toSet()));
        }

        return company;
    }

    private Set<CompanyExport> getData(boolean isExport, Company company, @NonNull List<Map> mapList) {
        return mapList
                .stream()
                .map(map -> {
                    var companyExport = new CompanyExport();
                    companyExport.setValue(nonNull(map.get(value))
                            ? map.get(value).toString()
                            : null);
                    companyExport.setYear(nonNull(map.get(year))
                            ? parseInt(map.get(year).toString())
                            : 0);

                    if (isExport)
                        companyExport.setCompanyExport(company);
                    else
                        companyExport.setCompanyImport(company);

                    return companyExport;
                })
                .collect(toSet());
    }

}
