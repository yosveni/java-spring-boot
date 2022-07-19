package com.linkapital.core.services.industrial_production_index.contract;

import com.linkapital.core.services.industrial_production_index.contract.to.PhysicalProductionTO;
import com.linkapital.core.services.industrial_production_index.contract.to.PhysicalProductionVariableTO;
import com.linkapital.core.services.industrial_production_index.contract.to.ResponsePhysicalProductionTO;
import com.linkapital.core.services.industrial_production_index.datasource.domain.PhysicalProduction;
import com.linkapital.core.services.industrial_production_index.datasource.domain.PhysicalProductionVariable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class PhysicalProductionBinderImpl implements PhysicalProductionBinder {

    @Override
    public PhysicalProduction bind(ResponsePhysicalProductionTO source) {
        if ( source == null ) {
            return null;
        }

        PhysicalProduction physicalProduction = new PhysicalProduction();

        physicalProduction.setTerritorialLevel( source.getTerritorialLevel() );
        physicalProduction.setCodeDescription( source.getCodeDescription() );
        if ( source.getDate() != null ) {
            physicalProduction.setDate( LocalDate.parse( source.getDate() ) );
        }

        return physicalProduction;
    }

    @Override
    public List<PhysicalProductionTO> bind(Collection<PhysicalProduction> source) {
        if ( source == null ) {
            return null;
        }

        List<PhysicalProductionTO> list = new ArrayList<PhysicalProductionTO>( source.size() );
        for ( PhysicalProduction physicalProduction : source ) {
            list.add( physicalProductionToPhysicalProductionTO( physicalProduction ) );
        }

        return list;
    }

    @Override
    public PhysicalProductionVariable bindToPhysicalProductionVariable(ResponsePhysicalProductionTO source) {
        if ( source == null ) {
            return null;
        }

        PhysicalProductionVariable physicalProductionVariable = new PhysicalProductionVariable();

        physicalProductionVariable.setName( source.getVariable() );
        physicalProductionVariable.setMeasureUnit( source.getMeasureUnit() );
        if ( source.getValue() != null ) {
            physicalProductionVariable.setValue( Double.parseDouble( source.getValue() ) );
        }

        return physicalProductionVariable;
    }

    protected PhysicalProductionVariableTO physicalProductionVariableToPhysicalProductionVariableTO(PhysicalProductionVariable physicalProductionVariable) {
        if ( physicalProductionVariable == null ) {
            return null;
        }

        PhysicalProductionVariableTO physicalProductionVariableTO = new PhysicalProductionVariableTO();

        if ( physicalProductionVariable.getId() != null ) {
            physicalProductionVariableTO.setId( physicalProductionVariable.getId() );
        }
        physicalProductionVariableTO.setMeasureUnit( physicalProductionVariable.getMeasureUnit() );
        physicalProductionVariableTO.setName( physicalProductionVariable.getName() );
        physicalProductionVariableTO.setValue( physicalProductionVariable.getValue() );

        return physicalProductionVariableTO;
    }

    protected PhysicalProductionTO physicalProductionToPhysicalProductionTO(PhysicalProduction physicalProduction) {
        if ( physicalProduction == null ) {
            return null;
        }

        PhysicalProductionTO physicalProductionTO = new PhysicalProductionTO();

        if ( physicalProduction.getId() != null ) {
            physicalProductionTO.setId( physicalProduction.getId() );
        }
        physicalProductionTO.setTerritorialLevel( physicalProduction.getTerritorialLevel() );
        physicalProductionTO.setCodeDescription( physicalProduction.getCodeDescription() );
        physicalProductionTO.setDate( physicalProduction.getDate() );
        physicalProductionTO.setMonthlyIndex( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getMonthlyIndex() ) );
        physicalProductionTO.setFixedBaseIndexWithoutSeasonalAdjustment( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getFixedBaseIndexWithoutSeasonalAdjustment() ) );
        physicalProductionTO.setIndexAccumulatedLast12Months( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getIndexAccumulatedLast12Months() ) );
        physicalProductionTO.setMonthlyPercentageChange( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getMonthlyPercentageChange() ) );
        physicalProductionTO.setPercentageChangeAccumulatedYear( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getPercentageChangeAccumulatedYear() ) );
        physicalProductionTO.setYearToDateIndex( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getYearToDateIndex() ) );
        physicalProductionTO.setPercentageChangeAccumulatedLast12Months( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getPercentageChangeAccumulatedLast12Months() ) );

        return physicalProductionTO;
    }
}
