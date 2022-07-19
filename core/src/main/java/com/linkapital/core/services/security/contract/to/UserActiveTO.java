package com.linkapital.core.services.security.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The active user data.")
@Getter
@Setter
public class UserActiveTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The email.")
    private String email;

}
