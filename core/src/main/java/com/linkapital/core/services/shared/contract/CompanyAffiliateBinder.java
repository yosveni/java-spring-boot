package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.company.datasource.domain.CompanyMainInfo;
import com.linkapital.core.services.shared.datasource.domain.Address;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;

import static com.linkapital.core.services.company.contract.enums.RegistrationSituation.getRegistrationSituation;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static java.util.Objects.nonNull;

@Mapper
public interface CompanyAffiliateBinder {

    //Level 2
    String cnpj = "cnpj";
    String openingDate = "dataAbertura";
    String socialReason = "razaoSocial";
    String situation = "situacao";
    String uf = "uf";
    String municipality = "municipio";

    Function<Map, Address> buildAddress = source -> {
        var address = new Address();
        address.setUf(nonNull(source.get(uf)) ? source.get(uf).toString() : null);
        address.setMunicipality(nonNull(source.get(municipality)) ? source.get(municipality).toString() : null);

        return address;
    };

    Function<Map, CompanyMainInfo> bindCompanyMainInfo = source -> {
        var companyMainInfo = new CompanyMainInfo();

        companyMainInfo.setCnpj(source.get(cnpj).toString());
        companyMainInfo.setOpeningDate(source.get(openingDate) == null
                ? null
                : LocalDateTime.parse(source.get(openingDate).toString(), ISO_ZONED_DATE_TIME));
        companyMainInfo.setSocialReason(source.get(socialReason) == null
                ? null
                : source.get(socialReason).toString());
        companyMainInfo.setRegistrationSituation(source.get(situation) == null
                ? null
                : getRegistrationSituation(source.get(situation).toString()));
        companyMainInfo.setAddress(buildAddress.apply(source));

        return companyMainInfo;
    };

}
