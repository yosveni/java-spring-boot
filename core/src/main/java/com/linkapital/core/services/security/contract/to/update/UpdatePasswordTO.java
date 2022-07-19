package com.linkapital.core.services.security.contract.to.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "All data needed to update a user password.")
@Getter
@Setter
public class UpdatePasswordTO {

    @ApiModelProperty(notes = "The old user password.", required = true)
    @NotEmpty
    private String oldPassword;

    @ApiModelProperty(notes = "The new user password.", required = true)
    @NotEmpty
    private String newPassword;

}