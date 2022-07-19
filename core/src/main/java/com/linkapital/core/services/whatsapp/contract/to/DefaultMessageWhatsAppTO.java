package com.linkapital.core.services.whatsapp.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The default message of whatsapp.")
@Getter
@Setter
public class DefaultMessageWhatsAppTO {

    @ApiModelProperty(value = "The message.")
    String message;

    public DefaultMessageWhatsAppTO withMessage(String message) {
        setMessage(message);
        return this;
    }

}
