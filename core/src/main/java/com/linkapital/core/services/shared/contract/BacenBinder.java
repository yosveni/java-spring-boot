package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.person.datasource.domain.DisabilitiesBacen;
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
public interface BacenBinder {

    BacenBinder BACEN_BINDER = Mappers.getMapper(BacenBinder.class);
    Logger log = getLogger(BacenBinder.class);

    //Level 2
    String penaltyPeriodDate = "dataPrazoFinalPenalidade";
    String publicationDate = "dataPublicacao";
    String penalty = "penalidade";
    String duration = "prazo";

    default List<DisabilitiesBacen> bind(List<Map> source) {
        return source
                .stream()
                .map(this::buildDisabilitiesBacen)
                .toList();
    }

    private DisabilitiesBacen buildDisabilitiesBacen(Map source) {
        var disabilitiesBacen = new DisabilitiesBacen();
        disabilitiesBacen.setDuration(nonNull(source.get(duration)) ? Integer.parseInt(source.get(duration).toString()) : 0);
        disabilitiesBacen.setPenalty(nonNull(source.get(penalty)) ? source.get(penalty).toString() : null);

        try {
            disabilitiesBacen.setPenaltyPeriodDate(nonNull(source.get(penaltyPeriodDate)) ? DateUtil.convert(source.get(penaltyPeriodDate).toString()) : null);
            disabilitiesBacen.setPublicationDate(nonNull(source.get(publicationDate)) ? DateUtil.convert(source.get(publicationDate).toString()) : null);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        return disabilitiesBacen;
    }

}
