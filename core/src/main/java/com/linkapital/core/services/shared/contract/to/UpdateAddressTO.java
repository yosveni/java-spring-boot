package com.linkapital.core.services.shared.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@ApiModel(description = "The data to create the address.")
@Getter
@Setter
public class UpdateAddressTO extends CreateAddressTO {

    @ApiModelProperty(value = "The id.")
    @Min(1)
    private long id;

}
