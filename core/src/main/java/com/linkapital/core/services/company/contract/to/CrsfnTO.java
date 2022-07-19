package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "All crsfn data.")
@Getter
@Setter
public class CrsfnTO {

    @ApiModelProperty(value = "The agreed number.")
    private String agreedNumber;

    @ApiModelProperty(value = "The process number.")
    private String processNumber;

    @ApiModelProperty(value = "The resource number.")
    private String resourceNumber;

    @ApiModelProperty(value = "The part.")
    private String part;

    @ApiModelProperty(value = "The resource type.")
    private String resourceType;

}
