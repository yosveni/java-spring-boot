package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MealProvidedTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The type.")
    private String type;

    @ApiModelProperty(value = "The quantity.")
    @NotNull
    private int quantity;

}
