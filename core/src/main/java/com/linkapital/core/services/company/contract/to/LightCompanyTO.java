package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "All company data (light).")
@Getter
@Setter
public class LightCompanyTO {

    @ApiModelProperty(value = "The company id", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The main information of the company.")
    private CompanyMainInfoTO mainInfo;

}
