package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The aircraft data.")
@Getter
@Setter
public class AircraftTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The aircraft manufacturer.")
    private String maker;

    @ApiModelProperty(value = "The registration number of the aircraft at the National Civil Aviation Agency.")
    private String registration;

    @ApiModelProperty(value = "The model.")
    private String model;

    @ApiModelProperty(value = "The status of the airworthiness certificate or aircraft registration.")
    private String situation;

    @ApiModelProperty(value = "The aircraft operation status in the Brazilian Aeronautical Registry (RAB).")
    private String statusRAB;

    @ApiModelProperty(value = "The operator name.")
    private String operatorName;

    @ApiModelProperty(value = "The owner name.")
    private String ownerName;

    @ApiModelProperty(value = "The production year.")
    private int productionYear;

}
