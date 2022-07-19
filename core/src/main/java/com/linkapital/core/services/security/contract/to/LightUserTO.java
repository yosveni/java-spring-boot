package com.linkapital.core.services.security.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The light user data.")
@Getter
@Setter
public class LightUserTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The email.")
    private String email;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The code country phone.")
    private String codeCountryPhone;

    @ApiModelProperty(value = "The phone number.")
    private String phone;

    @ApiModelProperty(value = "The role.")
    private RoleTO role;

}
