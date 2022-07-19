package com.linkapital.core.services.industrial_production_index.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The physical production variable data.")
@Getter
@Setter
public class PhysicalProductionVariableTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The measure unit.")
    private String measureUnit;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The value.")
    private double value;

}
