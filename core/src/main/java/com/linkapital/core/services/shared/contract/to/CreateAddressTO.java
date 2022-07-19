package com.linkapital.core.services.shared.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The data to create the address.")
@Getter
@Setter
public class CreateAddressTO {

    @ApiModelProperty(value = "The neighborhood.")
    private String neighborhood;

    @ApiModelProperty(value = "The zip.")
    private String zip;

    @ApiModelProperty(value = "The address1.")
    private String address1;

    @ApiModelProperty(value = "The address2.")
    private String address2;

    @ApiModelProperty(value = "The municipality.")
    private String municipality;

    @ApiModelProperty(value = "The number.")
    private String number;

    @ApiModelProperty(value = "The uf.")
    private String uf;

}
