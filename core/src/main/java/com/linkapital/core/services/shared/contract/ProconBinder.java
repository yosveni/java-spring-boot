package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.company.datasource.domain.Procon;
import com.linkapital.core.services.company.datasource.domain.ProconGroup;
import com.linkapital.core.services.company.datasource.domain.ProconProcesses;
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
public interface ProconBinder {

    ProconBinder PROCON_BINDER = Mappers.getMapper(ProconBinder.class);
    Logger log = getLogger(ProconBinder.class);

    //Level 1
    String procon = "proconInformacoes";
    //Level 2
    String group = "grupo";
    String totalPenaltyValue = "valorTotalMulta";
    //level 3
    String updateDate = "dataUltimaAtualizacao";
    String name = "nome";
    String proconGroup = "partesGrupo";
    String totalPenaltyValues = "valorTotalMultas";
    //level 4
    String cnpj = "cnpj";
    String status = "status";
    String penaltyValue = "valorMulta";
    String process = "processos";
    String processNumber = "numeroProcesso";

    default Procon binder(Map source) {
        var procon = new Procon();
        procon.setTotalPenaltyValue(nonNull(source.get(totalPenaltyValue)) ? Double.parseDouble(source.get(totalPenaltyValue).toString()) : 0);

        if (nonNull(source.get(group))) {
            var groupLevel2 = (Map) source.get(group);
            procon.setName(nonNull(groupLevel2.get(name)) ? groupLevel2.get(name).toString() : null);
            procon.setGroupPenaltyValue(nonNull(groupLevel2.get(totalPenaltyValues)) ? Double.parseDouble(groupLevel2.get(totalPenaltyValues).toString()) : 0);

            try {
                procon.setUpdateDate(nonNull(groupLevel2.get(updateDate)) ? DateUtil.convert(groupLevel2.get(updateDate).toString()) : null);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }

            if (nonNull(groupLevel2.get(proconGroup)))
                procon.getProconGroups().addAll(getProconGroup((List<Map>) groupLevel2.get(proconGroup)));

            if (nonNull(source.get(process)))
                procon.getProconProcesses().addAll(getProconProcesses((List<Map>) source.get(process)));
        }
        return procon;
    }

    private List<ProconGroup> getProconGroup(List<Map> source) {
        return source
                .stream()
                .map(map -> {
                    var proconGroup = new ProconGroup();
                    proconGroup.setCnpj(nonNull(map.get(cnpj)) ? map.get(cnpj).toString() : null);
                    proconGroup.setTotalPenaltyValue(nonNull(map.get(totalPenaltyValue)) ? Double.parseDouble(map.get(totalPenaltyValue).toString()) : 0);

                    return proconGroup;
                })
                .toList();
    }

    private List<ProconProcesses> getProconProcesses(List<Map> source) {
        return source
                .stream()
                .map(map -> {
                    var proconProcesses = new ProconProcesses();
                    proconProcesses.setStatus(nonNull(map.get(status)) ? map.get(status).toString() : null);
                    proconProcesses.setProcessNumber(nonNull(map.get(processNumber)) ? map.get(processNumber).toString() : null);
                    proconProcesses.setPenaltyValue(nonNull(map.get(penaltyValue)) ? Double.parseDouble(map.get(penaltyValue).toString()) : 0);

                    return proconProcesses;
                })
                .toList();
    }

}
