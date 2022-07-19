package com.linkapital.core.services.shared.impl.person;

import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.shared.PersonConverter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Map;

import static com.linkapital.core.services.shared.contract.FiscalBinder.bindDebitMte;
import static com.linkapital.core.services.shared.contract.FiscalBinder.bindDebitPgfnDau;

@Service
public class PersonFiscalConverterImpl implements PersonConverter {

    //Level 1
    private static final String personPgfnDau = "pessoaPgfnDau";
    private static final String mte = "mteCnd";

    private final PersonService personService;

    public PersonFiscalConverterImpl(PersonService personService) {
        this.personService = personService;
    }

    @PostConstruct
    public void init() {
        personService.addConverter(this);
    }

    @Override
    public Person convert(Person person, Map data) throws ParseException {
        if (data.get(personPgfnDau) != null)
            person.setDebitPgfnDau(bindDebitPgfnDau.apply((Map) data.get(personPgfnDau)));

        if (data.get(mte) != null)
            person.setDebitMte(bindDebitMte.apply((Map) data.get(mte)));

        return person;
    }

}
