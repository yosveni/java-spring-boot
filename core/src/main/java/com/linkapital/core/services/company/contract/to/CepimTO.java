package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The cepim data.")
@Getter
@Setter
public class CepimTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The grantor entity.")
    private String grantorEntity;

    @ApiModelProperty(value = "The reason for the impediment imposed on the researched company.")
    private String impediment;

    @ApiModelProperty(value = "The number of contract.")
    private String contract;

    @ApiModelProperty(value = "The value released.")
    private double valueReleased;

    @ApiModelProperty(value = "The end contract data.")
    private Date endContractDate;

}
