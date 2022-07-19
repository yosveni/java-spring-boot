package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.IbamaCnd;
import com.linkapital.core.services.company.datasource.domain.TaxHealth;
import com.linkapital.core.services.shared.CompanyConverter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.linkapital.core.services.shared.contract.FiscalBinder.bindDebitMte;
import static com.linkapital.core.services.shared.contract.FiscalBinder.bindDebitPgfnDau;
import static com.linkapital.core.services.shared.contract.FiscalBinder.bindInscriptionSintegra;
import static com.linkapital.core.services.shared.contract.FiscalBinder.bindSimpleNational;
import static com.linkapital.core.services.shared.contract.FiscalBinder.bindSuframa;
import static com.linkapital.core.services.shared.contract.FiscalBinder.getCnds;
import static java.lang.Boolean.parseBoolean;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

@Service
public class CompanyFiscalConverterImpl implements CompanyConverter {

    //Level 1
    private final String pgfnDau = "empresaPgfnDau";
    private final String mte = "mteCnd";
    private final String taxHealthCompany = "empresaSaudeTributaria";
    private final String simpleNational = "simplesNacional";
    private final String info = "info";
    private final String companySintegra = "empresaSintegra";
    private final String suframa = "suframaCadastro";
    private final String ibamaCnd = "ibamaCnd";
    //Level 2
    private final String taxHealth = "health";
    private final String cnds = "cnds";
    private final String pasiveISSS = "passivelIss";
    private final String inscriptions = "inscricoes";
    private final String certificate = "certidao";
    private final String emitDate = "emissao";
    private final String validDate = "validade";
    private final String situation = "situacao";

    private final CompanyService companyService;

    public CompanyFiscalConverterImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        this.companyService.addConverter(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) throws ParseException {
        if (data.get(pgfnDau) != null)
            company.setDebitPgfnDau(bindDebitPgfnDau.apply((Map) data.get(pgfnDau)));

        if (data.get(mte) != null)
            company.setDebitMte(bindDebitMte.apply((Map) data.get(mte)));

        if (data.get(taxHealthCompany) != null) {
            var taxHealthLevel1 = (Map) data.get(taxHealthCompany);
            var companyTaxHealth = new TaxHealth();

            companyTaxHealth.setTaxHealth(taxHealthLevel1.get(taxHealth) == null
                    ? "VERDE"
                    : taxHealthLevel1.get(taxHealth).toString());

            if (taxHealthLevel1.get(cnds) != null) {
                var cndsLevel2 = (List<Map>) taxHealthLevel1.get(cnds);

                if (cndsLevel2 != null)
                    companyTaxHealth.getCnds().addAll(getCnds.apply(cndsLevel2));
            }

            company.setTaxHealth(companyTaxHealth);
        }

        if (data.get(ibamaCnd) != null) {
            var ibamaCndLevel1 = (Map) data.get(ibamaCnd);
            var ibamaCnd = new IbamaCnd();

            ibamaCnd.setCertificateNumber(ibamaCndLevel1.get(certificate) == null
                    ? null
                    : ibamaCndLevel1.get(certificate).toString());
            ibamaCnd.setSituation(ibamaCndLevel1.get(situation) == null
                    ? null
                    : ibamaCndLevel1.get(situation).toString());
            ibamaCnd.setEmitDate(ibamaCndLevel1.get(emitDate) == null
                    ? null
                    : LocalDate.parse(ibamaCndLevel1.get(emitDate).toString(), ISO_ZONED_DATE_TIME));
            ibamaCnd.setValidDate(ibamaCndLevel1.get(validDate) == null
                    ? null
                    : LocalDate.parse(ibamaCndLevel1.get(validDate).toString(), ISO_ZONED_DATE_TIME));

            company.setIbamaCnd(ibamaCnd);
        }

        if (data.get(info) != null)
            company.setPassiveIISS(parseBoolean(((Map) data.get(info)).get(pasiveISSS).toString()));

        if (data.get(simpleNational) != null)
            company.setSimpleNational(bindSimpleNational.apply((Map) data.get(simpleNational)));

        if (data.get(companySintegra) != null) {
            var companySintegraLevel1 = (Map) data.get(companySintegra);
            var inscriptionsLevel1 = companySintegraLevel1.get(inscriptions) == null
                    ? null
                    : (List<Map>) companySintegraLevel1.get(inscriptions);

            if (inscriptionsLevel1 != null) {
                company.getSintegraInscriptions().clear();
                company.getSintegraInscriptions().addAll(bindInscriptionSintegra.apply(inscriptionsLevel1));
            }
        }

        if (data.get(suframa) != null)
            company.setSuframa(bindSuframa.apply((Map) data.get(suframa)));

        return company;
    }

}
