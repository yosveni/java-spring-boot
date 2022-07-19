package com.linkapital.core.services.commission.contract.to.create;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The data for create the commission percent.")
@Getter
@Setter
public class CreateCommissionPercentTO {

    @ApiModelProperty(value = "The month min.", required = true)
    protected int monthMin;

    @ApiModelProperty(value = "The month max.", required = true)
    protected int monthMax;

    @ApiModelProperty(value = "The percent.", required = true)
    protected double percent;

}
