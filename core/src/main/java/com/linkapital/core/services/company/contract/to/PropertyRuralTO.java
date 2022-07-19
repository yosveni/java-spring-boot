package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The property rural data in cafir.")
@Getter
@Setter
public class PropertyRuralTO {

    @ApiModelProperty(value = "The condominium.")
    private String condominium;

    @ApiModelProperty(value = "The municipality.")
    private String municipality;

    @ApiModelProperty(value = "The nirf.")
    private String nirf;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The type.")
    private String type;

    @ApiModelProperty(value = "The uf.")
    private String uf;


}
