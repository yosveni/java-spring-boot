package com.linkapital.core.services.method_k.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The score operation data.")
@Getter
@Setter
public class ScoreOperationTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The description.")
    private String description;

    @ApiModelProperty(value = "The value.")
    private double value;

}
