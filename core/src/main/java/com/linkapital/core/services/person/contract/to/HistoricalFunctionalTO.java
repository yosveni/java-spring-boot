package com.linkapital.core.services.person.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The data of the functional history of the person.")
@Getter
@Setter
public class HistoricalFunctionalTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The company CNPJ regarding the employee's bond.")
    private String cnpj;

    @ApiModelProperty(value = "The company social reason.")
    private String socialReason;

    @ApiModelProperty(value = "The Number of months the employee worked for the company.")
    private int months;

    @ApiModelProperty(value = "The admission date.")
    private Date admissionDate;

    @ApiModelProperty(value = "The dismissed date.")
    private Date dismissedDate;

}
