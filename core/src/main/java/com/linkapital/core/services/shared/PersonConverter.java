package com.linkapital.core.services.shared;

import com.linkapital.core.services.person.datasource.domain.Person;

import java.text.ParseException;
import java.util.Map;

public interface PersonConverter {

    Person convert(Person person, Map data) throws ParseException;

}
