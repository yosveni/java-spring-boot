package com.linkapital.core.services.commission.contract.to.create;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The data for create the commission payment percent.")
@Getter
@Setter
public class CreateCommissionPaymentPercentTO {

    @ApiModelProperty(value = "The disbursement.", required = true)
    protected double disbursement;

    @ApiModelProperty(value = "The amortization.", required = true)
    protected double amortization;

    @ApiModelProperty(value = "The liquidation.", required = true)
    protected double liquidation;

}
