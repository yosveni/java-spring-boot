package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The procon processes data.")
@Getter
@Setter
public class ProconProcessesTO {

    @ApiModelProperty(value = "The process number value.")
    private String processNumber;

    @ApiModelProperty(value = "The status value.")
    private String status;

    @ApiModelProperty(value = "The penalty value.")
    private double penaltyValue;

}
