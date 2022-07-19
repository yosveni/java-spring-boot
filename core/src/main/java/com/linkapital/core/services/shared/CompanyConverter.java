package com.linkapital.core.services.shared;

import com.linkapital.core.services.company.datasource.domain.Company;

import java.text.ParseException;
import java.util.Map;

public interface CompanyConverter {

    Company convert(Company company, Map data) throws ParseException;

}
