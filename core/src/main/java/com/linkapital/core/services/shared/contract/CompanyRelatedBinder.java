package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.company.datasource.domain.CompanyRelated;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

@Mapper
public interface CompanyRelatedBinder {

    String cnae = "cnae";
    String cnpj = "cnpj";
    String openingDate = "dataAbertura";
    String municipality = "municipio";
    String socialReason = "razaoSocial";
    String uf = "uf";

    Function<Map, CompanyRelated> build = source -> new CompanyRelated()
            .withCnpj(source.get(cnpj) == null
                    ? null
                    : source.get(cnpj).toString())
            .withSocialReason(source.get(socialReason) == null
                    ? null
                    : source.get(socialReason).toString())
            .withDescriptionCnae(source.get(cnae) == null
                    ? null
                    : source.get(cnae).toString())
            .withMunicipality(source.get(municipality) == null
                    ? null
                    : source.get(municipality).toString())
            .withUf(source.get(uf) == null
                    ? null
                    : source.get(uf).toString())
            .withOpeningDate(source.get(openingDate) == null
                    ? null
                    : LocalDateTime.parse(source.get(openingDate).toString(), ISO_ZONED_DATE_TIME));

}
