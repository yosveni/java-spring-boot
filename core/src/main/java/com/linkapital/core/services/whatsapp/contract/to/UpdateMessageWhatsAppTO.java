package com.linkapital.core.services.whatsapp.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "The message of whatsapp to update.")
@Getter
@Setter
public class UpdateMessageWhatsAppTO {

    @ApiModelProperty(value = "The message.", required = true)
    @NotEmpty
    private String message;

}
