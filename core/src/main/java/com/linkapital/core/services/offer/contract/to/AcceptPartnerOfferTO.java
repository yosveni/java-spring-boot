package com.linkapital.core.services.offer.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The data to accept the partner offer.")
@Getter
@Setter
public class AcceptPartnerOfferTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    @Min(1)
    private long id;

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private boolean accepted;

}
