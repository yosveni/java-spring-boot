package com.linkapital.core.services.ibge.contract;

import com.linkapital.core.services.ibge.contract.to.EconomicStatisticsTO;
import com.linkapital.core.services.ibge.contract.to.GeographicStatisticsTO;
import com.linkapital.core.services.ibge.contract.to.IbgeTO;
import com.linkapital.core.services.ibge.contract.to.WorkPerformanceStatisticsTO;
import com.linkapital.core.services.ibge.datasource.domain.EconomicStatistics;
import com.linkapital.core.services.ibge.datasource.domain.GeographicStatistics;
import com.linkapital.core.services.ibge.datasource.domain.Ibge;
import com.linkapital.core.services.ibge.datasource.domain.WorkPerformanceStatistics;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T22:29:28-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class IbgeBinderImpl implements IbgeBinder {

    @Override
    public IbgeTO bind(Ibge source) {
        if ( source == null ) {
            return null;
        }

        IbgeTO ibgeTO = new IbgeTO();

        if ( source.getId() != null ) {
            ibgeTO.setId( source.getId() );
        }
        ibgeTO.setGeographicStatistics( geographicStatisticsToGeographicStatisticsTO( source.getGeographicStatistics() ) );
        ibgeTO.setEconomicStatistics( economicStatisticsToEconomicStatisticsTO( source.getEconomicStatistics() ) );
        ibgeTO.setWorkPerformanceStatistics( workPerformanceStatisticsToWorkPerformanceStatisticsTO( source.getWorkPerformanceStatistics() ) );

        return ibgeTO;
    }

    protected GeographicStatisticsTO geographicStatisticsToGeographicStatisticsTO(GeographicStatistics geographicStatistics) {
        if ( geographicStatistics == null ) {
            return null;
        }

        GeographicStatisticsTO geographicStatisticsTO = new GeographicStatisticsTO();

        if ( geographicStatistics.getId() != null ) {
            geographicStatisticsTO.setId( geographicStatistics.getId() );
        }
        geographicStatisticsTO.setEstimatedPopulationYear( geographicStatistics.getEstimatedPopulationYear() );
        geographicStatisticsTO.setEstimatedPopulationLastCensusYear( geographicStatistics.getEstimatedPopulationLastCensusYear() );
        geographicStatisticsTO.setDemographicDensityYear( geographicStatistics.getDemographicDensityYear() );
        geographicStatisticsTO.setEstimatedPopulation( geographicStatistics.getEstimatedPopulation() );
        geographicStatisticsTO.setEstimatedPopulationLastCensus( geographicStatistics.getEstimatedPopulationLastCensus() );
        geographicStatisticsTO.setDemographicDensity( geographicStatistics.getDemographicDensity() );

        return geographicStatisticsTO;
    }

    protected EconomicStatisticsTO economicStatisticsToEconomicStatisticsTO(EconomicStatistics economicStatistics) {
        if ( economicStatistics == null ) {
            return null;
        }

        EconomicStatisticsTO economicStatisticsTO = new EconomicStatisticsTO();

        if ( economicStatistics.getId() != null ) {
            economicStatisticsTO.setId( economicStatistics.getId() );
        }
        economicStatisticsTO.setPibYear( economicStatistics.getPibYear() );
        economicStatisticsTO.setIdhmYear( economicStatistics.getIdhmYear() );
        economicStatisticsTO.setTotalRevenueYear( economicStatistics.getTotalRevenueYear() );
        economicStatisticsTO.setTotalExpensesYear( economicStatistics.getTotalExpensesYear() );
        economicStatisticsTO.setPercentageRevenueSourcesYear( economicStatistics.getPercentageRevenueSourcesYear() );
        economicStatisticsTO.setPib( economicStatistics.getPib() );
        economicStatisticsTO.setIdhm( economicStatistics.getIdhm() );
        economicStatisticsTO.setTotalRevenue( economicStatistics.getTotalRevenue() );
        economicStatisticsTO.setTotalExpenses( economicStatistics.getTotalExpenses() );
        economicStatisticsTO.setPercentageRevenueSources( economicStatistics.getPercentageRevenueSources() );

        return economicStatisticsTO;
    }

    protected WorkPerformanceStatisticsTO workPerformanceStatisticsToWorkPerformanceStatisticsTO(WorkPerformanceStatistics workPerformanceStatistics) {
        if ( workPerformanceStatistics == null ) {
            return null;
        }

        WorkPerformanceStatisticsTO workPerformanceStatisticsTO = new WorkPerformanceStatisticsTO();

        if ( workPerformanceStatistics.getId() != null ) {
            workPerformanceStatisticsTO.setId( workPerformanceStatistics.getId() );
        }
        workPerformanceStatisticsTO.setAverageSalaryYear( workPerformanceStatistics.getAverageSalaryYear() );
        workPerformanceStatisticsTO.setBusyPeopleYear( workPerformanceStatistics.getBusyPeopleYear() );
        workPerformanceStatisticsTO.setOccupiedPopulationYear( workPerformanceStatistics.getOccupiedPopulationYear() );
        workPerformanceStatisticsTO.setPopulationIncomeMonthlyNominalYear( workPerformanceStatistics.getPopulationIncomeMonthlyNominalYear() );
        workPerformanceStatisticsTO.setAverageSalary( workPerformanceStatistics.getAverageSalary() );
        workPerformanceStatisticsTO.setBusyPeople( workPerformanceStatistics.getBusyPeople() );
        workPerformanceStatisticsTO.setOccupiedPopulation( workPerformanceStatistics.getOccupiedPopulation() );
        workPerformanceStatisticsTO.setPopulationIncomeMonthlyNominal( workPerformanceStatistics.getPopulationIncomeMonthlyNominal() );

        return workPerformanceStatisticsTO;
    }
}
