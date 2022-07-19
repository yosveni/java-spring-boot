package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.shared.datasource.domain.JudicialProcess;
import com.linkapital.core.services.shared.datasource.domain.JudicialProcessQuantity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Mapper
public interface JudicialProcessBinder {

    JudicialProcessBinder JUDICIAL_PROCESS_BINDER = Mappers.getMapper(JudicialProcessBinder.class);

    //Level 2
    String judicialProcessQuantities = "quantidades";
    String totalValue = "valorTotal";
    String totalActiveValue = "valorTotalAtiva";
    String totalOthersValue = "valorTotalOutrasPartes";
    String totalPassiveValue = "valorTotalPassiva";
    //Level 3
    String quantityActive = "qtdAtivos";
    String quantityOthers = "qtdOutrasPartes";
    String quantityActivePart = "qtdParteAtiva";
    String quantityPassivePart = "qtdPartePassiva";
    String quantityTotal = "qtdTotal";
    String type = "tipo";

    default JudicialProcess bind(Map source) {
        var judicialProcess = new JudicialProcess();
        judicialProcess.setTotalValue(nonNull(source.get(totalValue)) ? Double.parseDouble(source.get(totalValue).toString()) : 0);
        judicialProcess.setTotalActiveValue(nonNull(source.get(totalActiveValue)) ? Double.parseDouble(source.get(totalActiveValue).toString()) : 0);
        judicialProcess.setTotalOthersValue(nonNull(source.get(totalOthersValue)) ? Double.parseDouble(source.get(totalOthersValue).toString()) : 0);
        judicialProcess.setTotalPassiveValue(nonNull(source.get(totalPassiveValue)) ? Double.parseDouble(source.get(totalPassiveValue).toString()) : 0);

        if (nonNull(source.get(judicialProcessQuantities)))
            judicialProcess.getJudicialProcessQuantities().addAll(getJudicialProcessQuantity((List<Map>) source.get(judicialProcessQuantities)));

        return judicialProcess;
    }

    private List<JudicialProcessQuantity> getJudicialProcessQuantity(List<Map> source) {
        return source
                .stream()
                .map(map -> {
                    var judicialProcessQuantity = new JudicialProcessQuantity();
                    judicialProcessQuantity.setQuantityActive(nonNull(map.get(quantityActive)) ? Integer.parseInt(map.get(quantityActive).toString()) : 0);
                    judicialProcessQuantity.setQuantityOthers(nonNull(map.get(quantityOthers)) ? Integer.parseInt(map.get(quantityOthers).toString()) : 0);
                    judicialProcessQuantity.setQuantityActivePart(nonNull(map.get(quantityActivePart)) ? Integer.parseInt(map.get(quantityActivePart).toString()) : 0);
                    judicialProcessQuantity.setQuantityPassivePart(nonNull(map.get(quantityPassivePart)) ? Integer.parseInt(map.get(quantityPassivePart).toString()) : 0);
                    judicialProcessQuantity.setQuantityTotal(nonNull(map.get(quantityTotal)) ? Integer.parseInt(map.get(quantityTotal).toString()) : 0);
                    judicialProcessQuantity.setType(nonNull(map.get(type)) ? map.get(type).toString() : null);

                    return judicialProcessQuantity;
                })
                .toList();
    }

}
