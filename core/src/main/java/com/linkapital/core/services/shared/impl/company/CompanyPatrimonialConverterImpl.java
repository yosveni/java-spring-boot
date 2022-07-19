package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.Domain;
import com.linkapital.core.services.company.datasource.domain.InpiBrand;
import com.linkapital.core.services.company.datasource.domain.InpiPatent;
import com.linkapital.core.services.company.datasource.domain.InpiSoftware;
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

import static com.linkapital.core.services.shared.contract.PropertiesBinder.bindCafir;
import static com.linkapital.core.services.shared.contract.PropertiesBinder.bindCompanyProperty;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Service
public class CompanyPatrimonialConverterImpl implements CompanyConverter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //Level 1
    private final String properties = "imoveis";
    private final String cafir = "cafir";
    private final String companyRegister = "empresaRegistroBr";
    private final String inpiBrand = "inpiMarcas";
    private final String inpiPatent = "empresaInpiPatentes";
    private final String inpiSoftware = "empresaInpiProgramas";
    //Level 2
    private final String domain = "dominios";
    private final String classBrand = "classe";
    private final String depositDate = "dataDeposito";
    private final String processNumber = "numeroProcesso";
    private final String situation = "situacao";
    private final String brand = "marca";
    private final String requests = "pedidos";
    private final String processes = "processos";
    //Level 3
    private final String modificationDate = "alterado";
    private final String createdDate = "criado";
    private final String nameDomain = "dominio";
    private final String expirationDate = "expiracao";
    private final String responsible = "responsavel";
    private final String concessionDate = "dataConcessao";
    private final String publicationDate = "dataPublicacao";
    private final String depositor = "depositante";
    private final String inventors = "inventores";
    private final String orderNumber = "numeroPedido";
    private final String procurator = "procurador";
    private final String title = "titulo";
    private final String authors = "autores";
    //Level 4
    private final String inventor = "inventor";
    private final String author = "autor";

    private final CompanyService companyService;

    public CompanyPatrimonialConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) {
        var propertiesLevel2 = data.get(properties) == null
                ? null
                : (List<Map>) data.get(properties);
        if (propertiesLevel2 != null) {
            var properties = company.getProperties();

            if (!properties.isEmpty())
                properties.clear();

            properties.addAll(bindCompanyProperty.apply(propertiesLevel2));
        }

        if (data.get(cafir) != null)
            company.setCafir(bindCafir.apply((Map) data.get(cafir)));

        var domainLevel2 = data.get(companyRegister) == null
                ? null
                : (List<Map>) ((Map) data.get(companyRegister)).get(domain);
        if (domainLevel2 != null) {
            var domains = company.getDomains();

            if (!domains.isEmpty())
                domains.clear();

            domains.addAll(domainLevel2
                    .stream()
                    .map(map -> {
                        var domain = new Domain();
                        domain.setName(nonNull(map.get(nameDomain))
                                ? map.get(nameDomain).toString()
                                : null);
                        domain.setResponsible(nonNull(map.get(responsible))
                                ? map.get(responsible).toString()
                                : null);

                        try {
                            domain.setCreatedDate(nonNull(map.get(createdDate))
                                    ? DateUtil.convert(map.get(createdDate).toString())
                                    : null);
                            domain.setModificationDate(nonNull(map.get(modificationDate))
                                    ? DateUtil.convert(map.get(modificationDate).toString())
                                    : null);
                            domain.setExpirationDate(nonNull(map.get(expirationDate))
                                    ? DateUtil.convert(map.get(expirationDate).toString())
                                    : null);
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        }

                        return domain;
                    })
                    .collect(toSet()));
        }

        if (data.get(inpiBrand) != null) {
            var inpiBrandLevel1 = (List<Map>) data.get(inpiBrand);
            var inpiBrands = company.getInpiBrands();

            if (!inpiBrands.isEmpty())
                inpiBrands.clear();

            inpiBrands.addAll(inpiBrandLevel1
                    .stream()
                    .map(map -> {
                        var inpiBrand = new InpiBrand();
                        inpiBrand.setClassBrand(nonNull(map.get(classBrand))
                                ? map.get(classBrand).toString()
                                : null);
                        inpiBrand.setProcessNumber(nonNull(map.get(processNumber))
                                ? map.get(processNumber).toString()
                                : null);
                        inpiBrand.setSituation(nonNull(map.get(situation))
                                ? map.get(situation).toString()
                                : null);
                        inpiBrand.setBrand(nonNull(map.get(brand))
                                ? map.get(brand).toString()
                                : null);

                        try {
                            inpiBrand.setDepositDate(nonNull(map.get(depositDate))
                                    ? DateUtil.convert(map.get(depositDate).toString())
                                    : null);
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        }

                        return inpiBrand;
                    })
                    .collect(toSet()));
        }

        var inventorsLevel3 = nonNull(data.get(inpiPatent))
                ? (List<Map>) ((Map) data.get(inpiPatent)).get(requests)
                : null;
        if (inventorsLevel3 != null) {
            var inpiPatents = company.getInpiPatents();

            if (!inpiPatents.isEmpty())
                inpiPatents.clear();

            inpiPatents.addAll(inventorsLevel3
                    .stream()
                    .map(map -> {
                        var inpiPatent = new InpiPatent();
                        inpiPatent.setDepositor(nonNull(map.get(depositor))
                                ? map.get(depositor).toString()
                                : null);
                        inpiPatent.setProcessNumber(nonNull(map.get(orderNumber))
                                ? map.get(orderNumber).toString()
                                : null);
                        inpiPatent.setProcurator(nonNull(map.get(procurator))
                                ? map.get(procurator).toString()
                                : null);
                        inpiPatent.setTitle(nonNull(map.get(title))
                                ? map.get(title).toString()
                                : null);
                        inpiPatent.setInventors(nonNull(map.get(inventors))
                                ? ((List<Map>) map.get(inventors))
                                .stream()
                                .map(map1 -> map1.get(inventor).toString())
                                .toList()
                                : null);

                        try {
                            inpiPatent.setConcessionDate(nonNull(map.get(concessionDate))
                                    ? DateUtil.convert(map.get(concessionDate).toString())
                                    : null);
                            inpiPatent.setDepositDate(nonNull(map.get(depositDate))
                                    ? DateUtil.convert(map.get(depositDate).toString())
                                    : null);
                            inpiPatent.setPublicationDate(nonNull(map.get(publicationDate))
                                    ? DateUtil.convert(map.get(publicationDate).toString())
                                    : null);
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        }

                        return inpiPatent;
                    })
                    .collect(toSet()));
        }

        var processesLevel2 = nonNull(data.get(inpiSoftware))
                ? (List<Map>) ((Map) data.get(inpiSoftware)).get(processes)
                : null;
        if (processesLevel2 != null) {
            var inpiSoftwares = company.getInpiSoftwares();

            if (!inpiSoftwares.isEmpty())
                inpiSoftwares.clear();

            inpiSoftwares.addAll(processesLevel2
                    .stream()
                    .map(map -> {
                        var inpiSoftware = new InpiSoftware();
                        inpiSoftware.setProcessNumber(nonNull(map.get(processNumber))
                                ? map.get(processNumber).toString()
                                : null);
                        inpiSoftware.setProcurator(nonNull(map.get(procurator))
                                ? map.get(procurator).toString()
                                : null);
                        inpiSoftware.setTitle(nonNull(map.get(title))
                                ? map.get(title).toString()
                                : null);
                        inpiSoftware.setAuthors(nonNull(map.get(authors))
                                ? ((List<Map>) map.get(authors))
                                .stream()
                                .map(map1 -> map1.get(author).toString())
                                .toList()
                                : null);

                        try {
                            inpiSoftware.setDepositDate(nonNull(map.get(depositDate))
                                    ? DateUtil.convert(map.get(depositDate).toString())
                                    : null);
                        } catch (ParseException e) {
                            log.error(e.getMessage());
                        }

                        return inpiSoftware;
                    })
                    .collect(toSet()));
        }

        return company;
    }

}
