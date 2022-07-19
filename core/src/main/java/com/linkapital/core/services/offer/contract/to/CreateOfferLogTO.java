package com.linkapital.core.services.offer.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The notification to create.")
@Getter
@Setter
public class CreateOfferLogTO {

    @ApiModelProperty(value = "The offer id.", required = true)
    @NotNull
    private Long offerId;

    @ApiModelProperty(value = "The notification description.", required = true)
    @NotEmpty
    private String notification;

}
