package com.linkapital.core.services.whatsapp.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The data to updated user whatsapp.")
@Getter
@Setter
public class UpdateUserWhatsAppTO extends BaseSaveUserWhatsAppTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

}
