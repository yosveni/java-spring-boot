package com.linkapital.core.services.person.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The data of the Income Tax and Physical Person.")
@Getter
@Setter
public class IrpfTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The bank name.")
    private String bank;

    @ApiModelProperty(value = "The agency number.")
    private String agency;

    @ApiModelProperty(value = "The lot number.")
    private String lot;

    @ApiModelProperty(value = "The statement status.")
    private String statementStatus;

    @ApiModelProperty(value = "The year exercise.")
    private int yearExercise;

    @ApiModelProperty(value = "The availability date.")
    private Date availabilityDate;

}
