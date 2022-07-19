package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "All employee growth data.")
@Getter
@Setter
public class EmployeeGrowthTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The year.")
    private int year;

    @ApiModelProperty(value = "The growth count")
    private int employeeGrowth;

    @ApiModelProperty(value = "The growth in percent")
    private double growth;

}
