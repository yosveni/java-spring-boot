package com.linkapital.core.services.shared.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The immovable property data.")
@Getter
@Setter
public class PropertyTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The property registration number registered with the real estate registry.")
    private String registryNumber;

    @ApiModelProperty(value = "The year the property was built, according to the property registration document.")
    private String buildingData;

    @ApiModelProperty(value = "The valuation value of the property, according to the property registration document.")
    private double evaluationValue;

    @ApiModelProperty(value = "The built area, according to the property registration document.")
    private double builtArea;

    @ApiModelProperty(value = "The land area, according to the property registration document.")
    private double groundArea;

    @ApiModelProperty(value = "The address.")
    private AddressTO address;

}
