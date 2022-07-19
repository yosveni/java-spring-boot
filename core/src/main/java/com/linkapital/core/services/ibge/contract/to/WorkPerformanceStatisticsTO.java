package com.linkapital.core.services.ibge.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The work and performance statistics data.")
@Getter
@Setter
public class WorkPerformanceStatisticsTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The last year of update of the average salary.")
    private int averageSalaryYear;

    @ApiModelProperty(value = "The last year of update of the busy people.")
    private int busyPeopleYear;

    @ApiModelProperty(value = "The last year of update of the occupied population.")
    private int occupiedPopulationYear;

    @ApiModelProperty(value = "The last year of update of the population income monthly nominal.")
    private int populationIncomeMonthlyNominalYear;

    @ApiModelProperty(value = "The average salary.")
    private double averageSalary;

    @ApiModelProperty(value = "The percent of busy people.")
    private double busyPeople;

    @ApiModelProperty(value = "The occupied population.")
    private double occupiedPopulation;

    @ApiModelProperty(value = "The population income monthly nominal.")
    private double populationIncomeMonthlyNominal;

}
