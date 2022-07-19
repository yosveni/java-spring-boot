package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.person.datasource.domain.HistoricalFunctional;
import com.linkapital.core.util.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;

@Mapper
public interface HistoricalFunctionalBinder {

    HistoricalFunctionalBinder HISTORICAL_FUNCTIONAL_BINDER = Mappers.getMapper(HistoricalFunctionalBinder.class);
    Logger log = getLogger(HistoricalFunctionalBinder.class);

    //Level 2
    String irpf = "exercicios";
    String cnpj = "cnpj";
    String admissionDate = "dataAdmissao";
    String dismissedDate = "dataDesligamento";
    String months = "numeroMesesEmpresa";
    String socialReason = "razaoSocial";

    default List<HistoricalFunctional> bind(List<Map> source) {
        return source
                .stream()
                .map(this::getHistoricalFunctional)
                .toList();
    }

    default HistoricalFunctional getHistoricalFunctional(Map source) {
        var historicalFunctional = new HistoricalFunctional();
        historicalFunctional.setCnpj(nonNull(source.get(cnpj)) ? source.get(cnpj).toString() : null);
        historicalFunctional.setSocialReason(nonNull(source.get(socialReason)) ? source.get(socialReason).toString() : null);
        historicalFunctional.setMonths(nonNull(source.get(months)) ? Integer.parseInt(source.get(months).toString()) : 0);

        try {
            historicalFunctional.setAdmissionDate(nonNull(source.get(admissionDate)) ? DateUtil.convert(source.get(admissionDate).toString()) : null);
            historicalFunctional.setDismissedDate(nonNull(source.get(dismissedDate)) ? DateUtil.convert(source.get(dismissedDate).toString()) : null);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        return historicalFunctional;
    }

}
