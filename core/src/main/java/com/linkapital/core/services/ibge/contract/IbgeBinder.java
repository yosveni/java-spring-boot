package com.linkapital.core.services.ibge.contract;

import com.linkapital.core.services.ibge.contract.to.IbgeIndicatorResponseTO;
import com.linkapital.core.services.ibge.contract.to.IbgeTO;
import com.linkapital.core.services.ibge.datasource.domain.EconomicStatistics;
import com.linkapital.core.services.ibge.datasource.domain.GeographicStatistics;
import com.linkapital.core.services.ibge.datasource.domain.Ibge;
import com.linkapital.core.services.ibge.datasource.domain.WorkPerformanceStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.function.Function;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Mapper
public interface IbgeBinder {

    IbgeBinder IBGE_BINDER = Mappers.getMapper(IbgeBinder.class);

    int _25207 = 25207;
    int _29168 = 29168;
    int _29171 = 29171;
    int _28141 = 28141;
    int _29749 = 29749;
    int _29763 = 29763;
    int _29765 = 29765;
    int _30255 = 30255;
    int _47001 = 47001;
    int _60036 = 60036;
    int _60037 = 60037;
    int _60048 = 60048;

    Function<List<IbgeIndicatorResponseTO>, Ibge> buildIbge = source -> {
        var estimatedPopulationLastCensusYear = 0;
        var demographicDensityYear = 0;
        var estimatedPopulationYear = 0;
        var estimatedPopulationLastCensus = 0D;
        var estimatedPopulation = 0D;
        var demographicDensity = 0D;

        var pibYear = 0;
        var idhmYear = 0;
        var totalRevenueYear = 0;
        var totalExpensesYear = 0;
        var percentageRevenueSourcesYear = 0;
        var pib = 0D;
        var idhm = 0D;
        var totalRevenue = 0D;
        var totalExpenses = 0D;
        var percentageRevenueSources = 0D;

        var averageSalaryYear = 0;
        var busyPeopleYear = 0;
        var occupiedPopulationYear = 0;
        var populationIncomeMonthlyNominalYear = 0;
        var averageSalary = 0D;
        var busyPeople = 0D;
        var occupiedPopulation = 0D;
        var populationIncomeMonthlyNominal = 0D;

        for (IbgeIndicatorResponseTO indicator : source) {
            if (!indicator.getRes().isEmpty()) {
                var map = indicator.getRes().get(0).getRes();

                if (!map.isEmpty()) {
                    var sorted = map.keySet()
                            .stream()
                            .sorted()
                            .toList();
                    var key = sorted.get(sorted.size() - 1);
                    var year = parseInt(key);
                    var value = parseDouble(map.get(key));

                    switch ((int) indicator.getId()) {
                        case _25207 -> {
                            estimatedPopulationLastCensusYear = year;
                            estimatedPopulationLastCensus = value;
                        }
                        case _29168 -> {
                            demographicDensityYear = year;
                            demographicDensity = value;
                        }
                        case _29171 -> {
                            estimatedPopulationYear = year;
                            estimatedPopulation = value;
                        }
                        case _28141 -> {
                            totalRevenueYear = year;
                            totalRevenue = value;
                        }
                        case _29749 -> {
                            totalExpensesYear = year;
                            totalExpenses = value;
                        }
                        case _30255 -> {
                            idhmYear = year;
                            idhm = value;
                        }
                        case _47001 -> {
                            pibYear = year;
                            pib = value;
                        }
                        case _60048 -> {
                            percentageRevenueSourcesYear = year;
                            percentageRevenueSources = value;
                        }
                        case _29763 -> {
                            busyPeopleYear = year;
                            busyPeople = value;
                        }
                        case _29765 -> {
                            averageSalaryYear = year;
                            averageSalary = value;
                        }
                        case _60036 -> {
                            occupiedPopulationYear = year;
                            occupiedPopulation = value;
                        }
                        default -> {
                            populationIncomeMonthlyNominalYear = year;
                            populationIncomeMonthlyNominal = value;
                        }
                    }
                }
            }
        }

        var geographicStatistics = new GeographicStatistics()
                .withEstimatedPopulationYear(estimatedPopulationYear)
                .withEstimatedPopulationLastCensusYear(estimatedPopulationLastCensusYear)
                .withDemographicDensityYear(demographicDensityYear)
                .withEstimatedPopulation(estimatedPopulation)
                .withEstimatedPopulationLastCensus(estimatedPopulationLastCensus)
                .withDemographicDensity(demographicDensity);

        var economicStatistics = new EconomicStatistics()
                .withPibYear(pibYear)
                .withIdhmYear(idhmYear)
                .withTotalRevenueYear(totalRevenueYear)
                .withTotalExpensesYear(totalExpensesYear)
                .withPercentageRevenueSourcesYear(percentageRevenueSourcesYear)
                .withPib(pib)
                .withIdhm(idhm)
                .withTotalRevenue(totalRevenue)
                .withTotalExpenses(totalExpenses)
                .withPercentageRevenueSources(percentageRevenueSources);

        var workPerformanceStatistics = new WorkPerformanceStatistics()
                .withBusyPeopleYear(busyPeopleYear)
                .withAverageSalaryYear(averageSalaryYear)
                .withOccupiedPopulationYear(occupiedPopulationYear)
                .withPopulationIncomeMonthlyNominalYear(populationIncomeMonthlyNominalYear)
                .withBusyPeople(busyPeople)
                .withAverageSalary(averageSalary)
                .withOccupiedPopulation(occupiedPopulation)
                .withPopulationIncomeMonthlyNominal(populationIncomeMonthlyNominal);

        return new Ibge()
                .withGeographicStatistics(geographicStatistics)
                .withEconomicStatistics(economicStatistics)
                .withWorkPerformanceStatistics(workPerformanceStatistics);
    };

    IbgeTO bind(Ibge source);

}
