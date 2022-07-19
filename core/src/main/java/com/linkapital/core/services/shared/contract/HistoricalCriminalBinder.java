package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.person.datasource.domain.HistoricalCriminal;
import com.linkapital.core.util.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;

import java.text.ParseException;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;

@Mapper
public interface HistoricalCriminalBinder {

    HistoricalCriminalBinder HISTORICAL_CRIMINAL_BINDER = Mappers.getMapper(HistoricalCriminalBinder.class);
    Logger log = getLogger(HistoricalCriminalBinder.class);

    //Level 2
    String consultationDate = "dataConsulta";
    String protocol = "protocolo";
    String situation = "situacao";
    String status = "status";

    default HistoricalCriminal bind(Map source) {
        var historicalCriminal = new HistoricalCriminal();
        historicalCriminal.setProtocol(nonNull(source.get(protocol)) ? source.get(protocol).toString() : null);
        historicalCriminal.setSituation(nonNull(source.get(situation)) ? source.get(situation).toString() : null);
        historicalCriminal.setStatus(nonNull(source.get(status)) ? source.get(status).toString() : null);

        try {
            historicalCriminal.setConsultationDate(nonNull(source.get(consultationDate)) ? DateUtil.convert(source.get(consultationDate).toString()) : null);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        return historicalCriminal;
    }

}
