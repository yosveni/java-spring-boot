package com.linkapital.core.services.shared.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The debit Pgfn data.")
@Getter
@Setter
public class DebitPgfnTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The nature.")
    private String nature;

    @ApiModelProperty(value = "The inscription number.")
    private String inscriptionNumber;

    @ApiModelProperty(value = "The debit.")
    private double debit;

}
