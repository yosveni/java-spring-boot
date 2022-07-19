package com.linkapital.core.services.shared.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The debit Mte process data.")
@Getter
@Setter
public class DebitMteProcessTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The number.")
    private String number;

    @ApiModelProperty(value = "The situation.")
    private String situation;

    @ApiModelProperty(value = "The infringement category.")
    private String infringementCategory;

    @ApiModelProperty(value = "The infringement capitulation.")
    private String infringementCapitulation;

}
