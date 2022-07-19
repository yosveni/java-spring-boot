package com.linkapital.core.services.offer_installment.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The data to update the installment of the offer.")
@Getter
@Setter
public class UpdateOfferInstallmentTO extends BaseOfferInstallmentTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private long id;

}
