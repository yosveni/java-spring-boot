package com.linkapital.core.services.company_user.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@ApiModel(description = "The company employees data.")
@Getter
@Setter
public class CompanyEmployeeTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The person cpf.")
    private String cpf;

    @ApiModelProperty(value = "The person name.")
    private String name;

    @ApiModelProperty(value = "The date of termination of the contract (mm-yyyy).")
    private String resignationYear;

    @ApiModelProperty(value = "The admission date.")
    private LocalDateTime admissionDate;

    @ApiModelProperty(value = "The birth date.")
    private LocalDateTime birthDate;


}
