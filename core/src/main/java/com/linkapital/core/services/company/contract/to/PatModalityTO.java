package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The feeding program modality data.")
@Getter
@Setter
public class PatModalityTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The provider cnpj.")
    private String providerCnpj;

    @ApiModelProperty(value = "The provider social reason.")
    private String providerSocialReason;

    @ApiModelProperty(value = "The mode.")
    private String mode;

    @ApiModelProperty(value = "The number of over sm.")
    private int over5Sm;

    @ApiModelProperty(value = "The number of sm.")
    private int to5Sm;

    @ApiModelProperty(value = "The number of benefited employees.")
    private int benefitedEmployees;

}
