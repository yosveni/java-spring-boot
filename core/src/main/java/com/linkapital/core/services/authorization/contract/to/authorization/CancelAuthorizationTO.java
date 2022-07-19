package com.linkapital.core.services.authorization.contract.to.authorization;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "The data to update Authorization by K agent.")
@Getter
@Setter
public class CancelAuthorizationTO {

    @ApiModelProperty(value = "The token.", required = true)
    @NotEmpty
    private String token;

    @ApiModelProperty(value = "The reason.", required = true)
    @NotEmpty
    private String cancelledReason;

}
