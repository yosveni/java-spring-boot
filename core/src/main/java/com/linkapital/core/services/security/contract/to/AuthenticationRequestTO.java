package com.linkapital.core.services.security.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

import static java.util.Locale.ROOT;

@ApiModel(description = "The user credentials to authenticate to the system.")
@Getter
@Setter
public class AuthenticationRequestTO implements Serializable {

    @ApiModelProperty(value = "The user email.", required = true)
    @NotEmpty
    @Email
    private String email;

    @ApiModelProperty(value = "The user password.", required = true)
    @NotEmpty
    private String password;

    public String getEmail() {
        return this.email.toLowerCase(ROOT);
    }

}