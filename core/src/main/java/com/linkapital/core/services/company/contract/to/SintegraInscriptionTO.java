package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The inscription sintegra data")
@Getter
@Setter
public class SintegraInscriptionTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The unique numerical code for each company, by Federal Unit (UF).")
    private String registrationNumber;

    @ApiModelProperty(value = "The registration situation.")
    private String registrationSituation;

    @ApiModelProperty(value = "Indicates whether the company is opting for SIMPLES, SIMEI, etc.")
    private String regime;

    @ApiModelProperty(value = "The email.")
    private String email;

    @ApiModelProperty(value = "The phone.")
    private String phone;

    @ApiModelProperty(value = "The uf.")
    private String uf;

    @ApiModelProperty(value = "The date on which the company changed its status to the current registration status.")
    private Date registrationSituationDate;

}
