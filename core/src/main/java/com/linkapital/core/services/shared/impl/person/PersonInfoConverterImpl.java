package com.linkapital.core.services.shared.impl.person;

import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.shared.PersonConverter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Map;

import static com.linkapital.core.services.shared.contract.PersonInfoBinder.PERSON_INFO_BINDER;

@Service
public class PersonInfoConverterImpl implements PersonConverter {

    private final PersonService personService;

    public PersonInfoConverterImpl(PersonService personService) {
        this.personService = personService;
    }

    @PostConstruct
    public void init() {
        personService.addConverter(this);
    }

    @Override
    public Person convert(Person person, Map data) throws ParseException {
        return PERSON_INFO_BINDER.bind(person, data);
    }

}
