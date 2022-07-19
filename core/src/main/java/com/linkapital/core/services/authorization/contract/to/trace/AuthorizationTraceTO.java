package com.linkapital.core.services.authorization.contract.to.trace;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The data referring to the host where the authorization is made.")
@Getter
@Setter
public class AuthorizationTraceTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The host ip.")
    private String ip;

    @ApiModelProperty(value = "The city.")
    private String city;

    @ApiModelProperty(value = "The country name.")
    private String countryName;

    @ApiModelProperty(value = "The CEP.")
    private String cep;

    @ApiModelProperty(value = "The navigator type.")
    private String navigator;

    @ApiModelProperty(value = "The device type.")
    private String device;

    @ApiModelProperty(value = "The latitude of the location.")
    private Double latitude;

    @ApiModelProperty(value = "The longitude of the location.")
    private Double longitude;

}
