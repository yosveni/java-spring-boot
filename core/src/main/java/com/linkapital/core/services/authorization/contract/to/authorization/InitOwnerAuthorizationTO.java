package com.linkapital.core.services.authorization.contract.to.authorization;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "The data to init Authorization.")
@Getter
@Setter
public class InitOwnerAuthorizationTO extends BaseOwnerAuthorizationTO {

    @ApiModelProperty(value = "The cnpj.", required = true)
    @NotEmpty
    private String cnpj;

    public InitOwnerAuthorizationTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

}
