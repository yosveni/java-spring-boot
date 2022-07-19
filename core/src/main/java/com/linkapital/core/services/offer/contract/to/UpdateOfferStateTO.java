package com.linkapital.core.services.offer.contract.to;

import com.linkapital.core.services.offer.contract.enums.OfferState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The data to change the status of the offer.")
@Getter
@Setter
public class UpdateOfferStateTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    @Min(1)
    private long id;

    @ApiModelProperty(value = "The state.", required = true)
    @NotNull
    private OfferState state;

}
