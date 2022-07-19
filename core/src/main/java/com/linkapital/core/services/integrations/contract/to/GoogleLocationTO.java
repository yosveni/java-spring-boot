package com.linkapital.core.services.integrations.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "The Google Maps API location")
@Getter
@Setter
@NoArgsConstructor
public class GoogleLocationTO {

    @ApiModelProperty(value = "The latitude")
    private double latitude;

    @ApiModelProperty(value = "The longitude")
    private double longitude;

    @ApiModelProperty(value = "The Formatted address")
    private String formattedAddress;

}
