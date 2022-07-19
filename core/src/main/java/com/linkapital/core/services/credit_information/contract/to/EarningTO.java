package com.linkapital.core.services.credit_information.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The earning data.")
@Getter
@Setter
public class EarningTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The code.")
    private String code;

    @ApiModelProperty(value = "The value.")
    private double value;

}
