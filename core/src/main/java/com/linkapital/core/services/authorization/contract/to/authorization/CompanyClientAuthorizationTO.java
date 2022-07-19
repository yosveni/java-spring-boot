package com.linkapital.core.services.authorization.contract.to.authorization;

import com.linkapital.core.services.authorization.contract.enums.AuthorizationState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The data of the authorization for this company.")
@Getter
@Setter
public class CompanyClientAuthorizationTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The authorization state.")
    private AuthorizationState state;

}
