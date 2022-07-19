package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The suframa data")
@Getter
@Setter
public class SuframaTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The SUFRAMA company registration number.")
    private String registrationNumber;

    @ApiModelProperty(value = "The registration situation.")
    private String registrationSituation;

    @ApiModelProperty(value = "The icms.")
    private String icms;

    @ApiModelProperty(value = "The ipi.")
    private String ipi;

    @ApiModelProperty(value = "The pisCofins.")
    private String pisCofins;

    @ApiModelProperty(value = "The expiration date of the company's registration with SUFRAMA")
    private Date expirationDate;

}
