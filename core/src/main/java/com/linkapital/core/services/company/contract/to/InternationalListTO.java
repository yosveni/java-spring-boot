package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The international list data.")
@Getter
@Setter
public class InternationalListTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The name of the international list that the person or company is listed on.")
    private String name;

    @ApiModelProperty(value = "The last data collection date for the name of the international list.")
    private Date queryDate;

}
