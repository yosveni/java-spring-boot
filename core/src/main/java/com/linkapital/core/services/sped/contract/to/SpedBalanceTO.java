package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(description = "The balance sped data.")
@Getter
@Setter
public class SpedBalanceTO extends SpedPatternTO {

    @ApiModelProperty(value = "The init date.")
    @NotNull
    private LocalDate initDate;

    @ApiModelProperty(value = "The end date.")
    @NotNull
    private LocalDate endDate;

}
