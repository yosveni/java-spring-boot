package com.linkapital.core.services.whatsapp.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The user whatsapp data.")
@Getter
@Setter
public class UserWhatsAppTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The user name.")
    private String name;

    @ApiModelProperty(value = "The last name.")
    private String lastName;

    @ApiModelProperty(value = "The number.")
    private String number;

    @ApiModelProperty(value = "The work position.")
    private String workPosition;

    @ApiModelProperty(value = "The times called.")
    private int timesCalled;

    @ApiModelProperty(value = "The work position.")
    private boolean enabled;

}
