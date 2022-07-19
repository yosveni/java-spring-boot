package com.linkapital.core.services.person.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The data of the criminal history.")
@Getter
@Setter
public class HistoricalCriminalTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The status.")
    private String status;

    @ApiModelProperty(value = "The situation.")
    private String situation;

    @ApiModelProperty(value = "The protocol.")
    private String protocol;

    @ApiModelProperty(value = "The consultation date.")
    private Date consultationDate;

}
