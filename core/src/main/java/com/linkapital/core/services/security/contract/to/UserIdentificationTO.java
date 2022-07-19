package com.linkapital.core.services.security.contract.to;

import com.linkapital.core.services.security.contract.enums.Intensity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The user data to analyze the identification process.")
@Getter
@Setter
public class UserIdentificationTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The email.")
    private String email;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "If is enabled.")
    private boolean enabled;

    @ApiModelProperty(value = "The data created.")
    private Date created;

    @ApiModelProperty(value = "The intensity.")
    private Intensity intensity;

    @ApiModelProperty(value = "The role.")
    private RoleTO role;

}
