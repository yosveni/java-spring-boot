package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.person.datasource.domain.Irpf;
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
public interface IrpfBinder {

    IrpfBinder IRPF_BINDER = Mappers.getMapper(IrpfBinder.class);
    Logger log = getLogger(IrpfBinder.class);

    //Level 2
    String irpf = "exercicios";
    String cnpj = "cnpj";
    String socialReason = "razaoSocial";
    //Level 3
    String bank = "nomeBanco";
    String agency = "numAgencia";
    String lot = "numLote";
    String statementStatus = "situacao";
    String yearExercise = "anoExercicio";
    String availabilityDate = "dataDisponibilidade";

    default List<Irpf> bind(List<Map> source) {
        return source
                .stream()
                .map(this::getIrpf)
                .toList();
    }

    private Irpf getIrpf(Map source) {
        var irpf = new Irpf();
        irpf.setBank(nonNull(source.get(bank)) ? source.get(bank).toString() : null);
        irpf.setAgency(nonNull(source.get(agency)) ? source.get(agency).toString() : null);
        irpf.setLot(nonNull(source.get(lot)) ? source.get(lot).toString() : null);
        irpf.setStatementStatus(nonNull(source.get(statementStatus)) ? source.get(statementStatus).toString() : null);
        irpf.setYearExercise(nonNull(source.get(yearExercise)) ? Integer.parseInt(source.get(yearExercise).toString()) : 0);

        try {
            irpf.setAvailabilityDate(nonNull(source.get(availabilityDate)) ? DateUtil.convert(source.get(availabilityDate).toString()) : null);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        return irpf;
    }

}
