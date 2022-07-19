package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ApiModel(description = "The Simple National data.")
@Getter
@Setter
public class SimpleNationalTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "Indicates if the company that is opting for the SIMEI Tax Regime.")
    private boolean simei;

    @ApiModelProperty(value = "Indicates if the company that is opting for the SIMPLE Tax Regime.")
    private boolean simple;

    @ApiModelProperty(value = "Indicates if the companies that have a CNAE as Main CNAE that does not allow the option" +
            " for SIMEI and SIMPLE.")
    private boolean simpleIrregular;

    @ApiModelProperty(value = "The date on which the entrepreneur opted for the SIMEI regime.")
    private Date simeiDate;

    @ApiModelProperty(value = "The date on which the entrepreneur opted for the SIMPLE regime.")
    private Date simpleDate;

}
