package com.linkapital.core.services.industrial_production_index.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@ApiModel(description = "The physical production data.")
@Getter
@Setter
public class PhysicalProductionTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The territorial level.")
    private String territorialLevel;

    @ApiModelProperty(value = "The code description.")
    private String codeDescription;

    @ApiModelProperty(value = "The date.")
    private LocalDate date;

    @ApiModelProperty(value = "The monthly index.")
    private PhysicalProductionVariableTO monthlyIndex;

    @ApiModelProperty(value = "The fixed base index without seasonal adjustment.")
    private PhysicalProductionVariableTO fixedBaseIndexWithoutSeasonalAdjustment;

    @ApiModelProperty(value = "The index accumulated in the last 12 months.")
    private PhysicalProductionVariableTO indexAccumulatedLast12Months;

    @ApiModelProperty(value = "The monthly percentage change.")
    private PhysicalProductionVariableTO monthlyPercentageChange;

    @ApiModelProperty(value = "The percentage change accumulated year.")
    private PhysicalProductionVariableTO percentageChangeAccumulatedYear;

    @ApiModelProperty(value = "The year to date index.")
    private PhysicalProductionVariableTO yearToDateIndex;

    @ApiModelProperty(value = "The percentage change accumulated in the last 12 months.")
    private PhysicalProductionVariableTO percentageChangeAccumulatedLast12Months;

}
