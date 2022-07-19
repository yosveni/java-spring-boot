package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The judicial process quantity data")
@Getter
@Setter
public class JudicialProcessQuantityTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The type value.")
    private String type;

    @ApiModelProperty(value = "The quantity total values.")
    private int quantityTotal;

    @ApiModelProperty(value = "The quantity active values.")
    private int quantityActive;

    @ApiModelProperty(value = "The quantity others values.")
    private int quantityOthers;

    @ApiModelProperty(value = "The quantity active part values.")
    private int quantityActivePart;

    @ApiModelProperty(value = "The quantity passive part values.")
    private int quantityPassivePart;

}
