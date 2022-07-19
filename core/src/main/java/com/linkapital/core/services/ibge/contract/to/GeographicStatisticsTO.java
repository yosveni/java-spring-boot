package com.linkapital.core.services.ibge.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The geographic statistics data.")
@Getter
@Setter
public class GeographicStatisticsTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The last year of update of the estimated population.")
    private int estimatedPopulationYear;

    @ApiModelProperty(value = "The last year of update of the population census.")
    private int estimatedPopulationLastCensusYear;

    @ApiModelProperty(value = "The last year of update of the demographic density.")
    private int demographicDensityYear;

    @ApiModelProperty(value = "The estimated population.")
    private double estimatedPopulation;

    @ApiModelProperty(value = "The estimated population last census.")
    private double estimatedPopulationLastCensus;

    @ApiModelProperty(value = "The demographic density.")
    private double demographicDensity;

}
