package com.linkapital.core.services.shared.impl.company;

import com.linkapital.core.services.company.CompanyPartnersService;
import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.CompanyPartners;
import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.shared.CompanyConverter;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.linkapital.core.services.shared.contract.PartnerBinder.PARTNER_BINDER;
import static org.springframework.util.StringUtils.hasText;

@Service
@Transactional
public class CompanyPartnersConverterImpl implements CompanyConverter {

    //Level 1
    private final String partner = "socios";
    private final String partnerBoard = "sociosJunta";
    private final String partnerQsa = "qsaUnificado";
    //Level 2
    private final String document = "documento";
    private final CompanyService companyService;
    private final PersonService personService;
    private final CompanyPartnersService companyPartnersService;

    public CompanyPartnersConverterImpl(CompanyService companyService,
                                        PersonService personService,
                                        CompanyPartnersService companyPartnersService) {
        this.companyService = companyService;
        this.personService = personService;
        this.companyPartnersService = companyPartnersService;
    }

    @PostConstruct
    public void init() {
        this.companyService.addConverterDependent(this);
    }

    @Override
    public Company convert(Company company, @NonNull Map data) {
        var companyPartnersList = new ArrayList<CompanyPartners>();
        List<Map> partnersLevel1 = null;

        if (data.get(partnerBoard) != null)
            partnersLevel1 = (List<Map>) data.get(partnerBoard);
        else if (data.get(partner) != null)
            partnersLevel1 = (List<Map>) data.get(partner);

        Optional
                .ofNullable(partnersLevel1)
                .ifPresent(mapList -> mapList
                        .forEach(partnerMap -> {
                            var documentPartner = partnerMap.get(document) == null
                                    ? null
                                    : partnerMap.get(document).toString();

                            if (hasText(documentPartner)) {
                                var companyPartners = PARTNER_BINDER.bindDefaultCompanyPartners(company,
                                        partnerMap);

                                if (documentPartner.length() > 11)
                                    companyPartnersList.add(companyPartners
                                            .withCompanyPartner(getCompany(documentPartner, partnerMap)));
                                else
                                    companyPartnersList.add(companyPartners
                                            .withPerson(getPerson(documentPartner, partnerMap)));
                            }
                        }));

        if (company.isHasDivergentQSA() && data.get(partnerQsa) != null)
            PARTNER_BINDER.updateCompanyPartners(companyPartnersList, (List<Map>) data.get(partnerQsa));

        company.getPartners().clear();
        company.getPartners().addAll(companyPartnersService.saveAll(companyPartnersList));

        return company;
    }

    private Person getPerson(String cpf, Map data) {
        var atomicReference = new AtomicReference<Person>();
        Optional
                .ofNullable(personService.getByCpfAux(cpf))
                .ifPresentOrElse(atomicReference::set,
                        () -> atomicReference.set(personService.save(PARTNER_BINDER.bindDefaultPerson(data))));

        return atomicReference.get();
    }

    private Company getCompany(String cnpj, Map data) {
        var atomicReference = new AtomicReference<Company>();
        Optional
                .ofNullable(companyService.getByCnpjAux(cnpj))
                .ifPresentOrElse(atomicReference::set,
                        () -> atomicReference.set(
                                companyService.save(PARTNER_BINDER.buildCompanyForCompanyPartners(data)))
                );

        return atomicReference.get();
    }

}
