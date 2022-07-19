package com.linkapital.core.services.directory.contract.to;

import com.linkapital.core.services.directory.contract.enums.Type;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "All directory data.")
@Getter
@Setter
public class DirectoryTO {

    @ApiModelProperty(value = "The company id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The ext.")
    private String ext;

    @ApiModelProperty(value = "The url.")
    private String url;

    @ApiModelProperty(value = "The type.")
    private Type type;

}
