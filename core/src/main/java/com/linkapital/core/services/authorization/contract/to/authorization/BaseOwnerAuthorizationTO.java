package com.linkapital.core.services.authorization.contract.to.authorization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BaseOwnerAuthorizationTO {

    @ApiModelProperty(value = "The name.", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "The email.", required = true)
    @NotEmpty
    @Email
    private String email;

    @ApiModelProperty(value = "The user cpf.", required = true)
    @NotEmpty
    private String cpf;

}
