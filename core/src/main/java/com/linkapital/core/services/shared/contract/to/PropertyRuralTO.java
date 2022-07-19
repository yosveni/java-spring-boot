package com.linkapital.core.services.shared.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The immovable property rural data.")
@Getter
@Setter
public class PropertyRuralTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The property number at the IRS. It is used to identify the property.")
    private String nirf;

    @ApiModelProperty(value = "The name of the rural property, defined by its owner when registering with Cafir.")
    private String name;

    @ApiModelProperty(value = "The options yes or no to indicate whether the rural property is located in a condominium.")
    private String condominium;

    @ApiModelProperty(value = "The municipality in which the rural property is located.")
    private String municipality;

    @ApiModelProperty(value = "Indicates the person's link to the rural property.")
    private String type;

    @ApiModelProperty(value = "The Federative Unit of location of the rural property.")
    private String uf;

    @ApiModelProperty(value = "The total area of the rural property, measured in hectares.")
    private double area;

}
