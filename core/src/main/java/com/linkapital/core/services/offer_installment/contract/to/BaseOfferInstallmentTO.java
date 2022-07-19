package com.linkapital.core.services.offer_installment.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The offer installment data.")
@Getter
@Setter
public class BaseOfferInstallmentTO {

    @ApiModelProperty(value = "The total payment.", required = true)
    @NotNull
    private double total;

    @ApiModelProperty(value = "if it was paid.", required = true)
    @NotNull
    private boolean hasPaid;

    @ApiModelProperty(value = "The expiration date.", required = true)
    @NotNull
    private Date expiration;

}
