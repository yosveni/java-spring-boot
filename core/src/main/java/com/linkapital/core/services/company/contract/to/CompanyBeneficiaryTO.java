package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The data of the beneficiary company.")
@Getter
@Setter
public class CompanyBeneficiaryTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The document.")
    private String document;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The grade.")
    private int grade;

    @ApiModelProperty(value = "The grade QSA.")
    private int gradeQsa;

    @ApiModelProperty(value = "The participation.")
    private double participation;

    @ApiModelProperty(value = "The participation QSA.")
    private double participationQsa;

    @ApiModelProperty(value = "Indicates if the beneficiary is deceased or not.")
    private boolean dead;

}
