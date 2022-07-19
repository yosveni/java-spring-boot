package com.linkapital.core.services.shared.impl.person;

import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.person.datasource.domain.CorporateParticipation;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.shared.PersonConverter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.linkapital.core.services.shared.contract.PartnerBinder.bindRelationships;
import static com.linkapital.core.services.shared.contract.PartnerBinder.buildCorporateParticipation;
import static com.linkapital.core.services.shared.contract.PartnerBinder.updateCorporateParticipationRF;
import static java.util.stream.Collectors.toSet;

@Service
public class PersonPartnerConverterImpl implements PersonConverter {

    //Level 1
    private static final String partner = "participacaoSocietariaUnico";
    private static final String partnerRF = "participacaoSocietariaRF";
    private static final String relationships = "relacionamentos";
    private final PersonService personService;

    public PersonPartnerConverterImpl(PersonService personService) {
        this.personService = personService;
    }

    @PostConstruct
    public void init() {
        personService.addConverterDependent(this);
    }

    @Override
    public Person convert(Person person, Map data) throws ParseException {
        var list = new HashSet<CorporateParticipation>();
        var partnerLevel1 = data.get(partner) == null
                ? null
                : (List<Map>) data.get(partner);
        if (partnerLevel1 != null)
            list.addAll(partnerLevel1
                    .stream()
                    .map(buildCorporateParticipation)
                    .collect(toSet()));

        var partnerRFLevel1 = data.get(partnerRF) == null
                ? null
                : (List<Map>) data.get(partnerRF);
        if (partnerRFLevel1 != null)
            updateCorporateParticipationRF.accept(partnerRFLevel1, list);

        person.getCorporatesParticipation().clear();
        person.getCorporatesParticipation().addAll(list);

        if (data.get(relationships) != null) {
            person.getRelationships().clear();
            person.getRelationships().addAll(bindRelationships.apply((List<Map>) data.get(relationships)));
        }

        return person;
    }

}
