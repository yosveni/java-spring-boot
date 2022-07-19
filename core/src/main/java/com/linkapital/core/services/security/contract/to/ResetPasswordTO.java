package com.linkapital.core.services.security.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

import static java.util.Locale.ROOT;

@ApiModel(description = "All data needed to reset a user password.")
@Setter
public class ResetPasswordTO {

    @ApiModelProperty(value = "The user id.", required = true)
    @NotEmpty
    private String email;

    @ApiModelProperty(value = "The user password.", required = true)
    @NotEmpty
    private String password;

    public String getEmail() {
        return email.toLowerCase(ROOT);
    }

    public String getPassword() {
        return password;
    }

}
