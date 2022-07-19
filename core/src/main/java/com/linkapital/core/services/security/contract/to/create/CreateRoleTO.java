package com.linkapital.core.services.security.contract.to.create;

import com.linkapital.core.services.security.contract.enums.Authority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "All data needed to logout a role.")
@Getter
@Setter
public class CreateRoleTO {

    @ApiModelProperty(notes = "The name.", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(notes = "The code.", required = true)
    @NotEmpty
    private String code;

    @ApiModelProperty(value = "The description.")
    private String description;

    @ApiModelProperty(notes = "The authority.")
    @NotNull
    private Authority authority;

}