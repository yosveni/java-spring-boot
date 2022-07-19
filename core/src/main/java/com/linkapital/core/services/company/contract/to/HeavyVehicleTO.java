package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The vehicle info data.")
@Getter
@Setter
public class HeavyVehicleTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The type of fuel supported by the vehicle.")
    private String fuel;

    @ApiModelProperty(value = "The brand and model.")
    private String model;

    @ApiModelProperty(value = "The combination of letters and numbers to identify a vehicle.")
    private String carPlate;

    @ApiModelProperty(value = "The information and history.")
    private String renavam;

    @ApiModelProperty(value = "The type.")
    private String type;

    @ApiModelProperty(value = "The Federative Unit (UF) where the vehicle is registered.")
    private String uf;

    @ApiModelProperty(value = "The yes or no options to indicate whether the vehicle is registered with the National Land Transport Agency.")
    private boolean antt;

    @ApiModelProperty(value = "The year the vehicle was manufactured.")
    private int productionYear;

}
