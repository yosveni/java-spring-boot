package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The data of the company's beneficiaries (light).")
@Getter
@Setter
public class LightCompanyBeneficiariesTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The grade.")
    private int grade;

    @ApiModelProperty(value = "The participation.")
    private double participation;

}
