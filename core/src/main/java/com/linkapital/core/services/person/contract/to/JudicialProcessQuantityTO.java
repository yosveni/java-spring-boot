package com.linkapital.core.services.person.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The detail of the judicial process.")
@Getter
@Setter
public class JudicialProcessQuantityTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The type.")
    private String type;

    @ApiModelProperty(value = "The quantity of active.")
    private int quantityActive;

    @ApiModelProperty(value = "The quantity of active part.")
    private int quantityActivePart;

    @ApiModelProperty(value = "The quantity of passive part.")
    private int quantityPassivePart;

    @ApiModelProperty(value = "The quantity of others.")
    private int quantityOthers;

    @ApiModelProperty(value = "The total quantity.")
    private int quantityTotal;

}
