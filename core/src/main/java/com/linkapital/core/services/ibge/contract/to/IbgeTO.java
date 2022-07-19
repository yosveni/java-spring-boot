package com.linkapital.core.services.ibge.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The IBGE statistics data.")
@Getter
@Setter
public class IbgeTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The geographic statistics data.")
    private GeographicStatisticsTO geographicStatistics;

    @ApiModelProperty(value = "The economic statistics data.")
    private EconomicStatisticsTO economicStatistics;

    @ApiModelProperty(value = "The work and performance statistics data.")
    private WorkPerformanceStatisticsTO workPerformanceStatistics;

}
