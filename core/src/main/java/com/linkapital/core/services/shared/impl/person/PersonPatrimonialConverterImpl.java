package com.linkapital.core.services.shared.impl.person;

import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.shared.PersonConverter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static com.linkapital.core.services.shared.contract.PropertiesBinder.bindCafir;
import static com.linkapital.core.services.shared.contract.PropertiesBinder.bindPersonProperty;

@Service
public class PersonPatrimonialConverterImpl implements PersonConverter {

    private static final String cafir = "cafir";
    private static final String properties = "imoveis";

    private final PersonService personService;

    public PersonPatrimonialConverterImpl(PersonService personService) {
        this.personService = personService;
    }

    @PostConstruct
    public void init() {
        personService.addConverter(this);
    }

    @Override
    public Person convert(Person person, Map data) throws ParseException {
        if (data.get(cafir) != null)
            person.setCafir(bindCafir.apply((Map) data.get(cafir)));

        var propertiesLevel2 = data.get(properties) == null
                ? null
                : (List<Map>) data.get(properties);
        if (propertiesLevel2 != null) {
            person.getProperties().clear();
            person.getProperties().addAll(bindPersonProperty.apply(propertiesLevel2));
        }

        return person;
    }

}
