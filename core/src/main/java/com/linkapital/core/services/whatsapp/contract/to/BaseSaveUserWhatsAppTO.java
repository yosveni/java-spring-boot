package com.linkapital.core.services.whatsapp.contract.to;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public abstract class BaseSaveUserWhatsAppTO {

    @ApiModelProperty(value = "The user name.", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "The last name.")
    private String lastName;

    @ApiModelProperty(value = "The number.", required = true)
    @NotEmpty
    private String number;

    @ApiModelProperty(value = "The work position.")
    private String workPosition;

}
