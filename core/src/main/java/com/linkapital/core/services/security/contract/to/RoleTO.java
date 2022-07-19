package com.linkapital.core.services.security.contract.to;

import com.linkapital.core.services.security.contract.enums.Authority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The role data.")
@Getter
@Setter
public class RoleTO {

    @ApiModelProperty(value = "The id.")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The code.")
    private String code;

    @ApiModelProperty(value = "The description.")
    private String description;

    @ApiModelProperty(value = "The authority.")
    private Authority authority;

}
