package com.linkapital.core.services.industrial_production_index.contract;

import com.linkapital.core.services.industrial_production_index.contract.to.PhysicalProductionTO;
import com.linkapital.core.services.industrial_production_index.contract.to.ResponsePhysicalProductionTO;
import com.linkapital.core.services.industrial_production_index.datasource.domain.PhysicalProduction;
import com.linkapital.core.services.industrial_production_index.datasource.domain.PhysicalProductionVariable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

import static com.linkapital.core.services.industrial_production_index.contract.enums.PhysicalProductionVariableNames.fromString;

/**
 * Default binding class for {@link PhysicalProduction} transformations
 * Class in charge of constructing the necessary resources to upload or retrieve data from file storage
 *
 * @since 0.0.1
 */
@Mapper
public interface PhysicalProductionBinder {

    PhysicalProductionBinder PHYSICAL_PRODUCTION_BINDER = Mappers.getMapper(PhysicalProductionBinder.class);
    Logger log = LoggerFactory.getLogger(PhysicalProductionBinder.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "monthlyIndex", ignore = true)
    @Mapping(target = "fixedBaseIndexWithoutSeasonalAdjustment", ignore = true)
    @Mapping(target = "indexAccumulatedLast12Months", ignore = true)
    @Mapping(target = "monthlyPercentageChange", ignore = true)
    @Mapping(target = "percentageChangeAccumulatedYear", ignore = true)
    @Mapping(target = "yearToDateIndex", ignore = true)
    @Mapping(target = "percentageChangeAccumulatedLast12Months", ignore = true)
    PhysicalProduction bind(ResponsePhysicalProductionTO source);

    List<PhysicalProductionTO> bind(Collection<PhysicalProduction> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "variable", target = "name")
    PhysicalProductionVariable bindToPhysicalProductionVariable(ResponsePhysicalProductionTO source);

    default PhysicalProduction bind(List<ResponsePhysicalProductionTO> responses) {
        if (responses == null || responses.isEmpty())
            return null;

        var physicalProduction = bind(responses.get(0));
        responses.forEach(response -> {
            var productionVariable = bindToPhysicalProductionVariable(response);

            switch (fromString(productionVariable.getName())) {
                case MONTHLY_INDEX -> physicalProduction.setMonthlyIndex(productionVariable);
                case FIXED_BASE_INDEX_WITHOUT_SEASONAL_ADJUSTMENT -> physicalProduction.setFixedBaseIndexWithoutSeasonalAdjustment(productionVariable);
                case INDEX_ACCUMULATED_LAST_12_MONTHS -> physicalProduction.setIndexAccumulatedLast12Months(productionVariable);
                case MONTHLY_PERCENTAGE_CHANGE -> physicalProduction.setMonthlyPercentageChange(productionVariable);
                case PERCENTAGE_CHANGE_ACCUMULATED_YEAR -> physicalProduction.setPercentageChangeAccumulatedYear(productionVariable);
                case YEAR_TO_DATE_INDEX -> physicalProduction.setYearToDateIndex(productionVariable);
                case PERCENTAGE_CHANGE_ACCUMULATED_LAST_12_MONTHS -> physicalProduction.setPercentageChangeAccumulatedLast12Months(productionVariable);
                default -> {
                }
            }
        });

        return physicalProduction;
    }

}
