package com.linkapital.core.services.person.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The disabilities BACEN data.")
@Getter
@Setter
public class DisabilitiesBacenTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The penalty imposed on Individuals by BACEN (Central Bank of Brazil).")
    private String penalty;

    @ApiModelProperty(value = "The disabling period (in years).")
    private int duration;

    @ApiModelProperty(value = "The final date of the penalty imposed on individuals by BACEN (Central Bank of Brazil).")
    private Date penaltyPeriodDate;

    @ApiModelProperty(value = "The date of publication of the disqualification imposed by BACEN (Central Bank of Brazil).")
    private Date publicationDate;

}
