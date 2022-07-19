package com.linkapital.core.services.bank_operation.contract.to;

import com.linkapital.core.services.bank_operation.contract.enums.BankOperationStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ApiModel(description = "The bank operation data.")
@Getter
@Setter
public class BankOperationTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The uf.")
    private String uf;

    @ApiModelProperty(value = "The product name.")
    private String product;

    @ApiModelProperty(value = "The operation type.")
    private String type;

    @ApiModelProperty(value = "The financial cost.")
    private String financialCost;

    @ApiModelProperty(value = "The financial agent.")
    private String financialAgent;

    @ApiModelProperty(value = "The grace period (month).")
    private int gracePeriod;

    @ApiModelProperty(value = "The amortization period (month).")
    private int amortizationPeriod;

    @ApiModelProperty(value = "The tax(% a.a).")
    private double tax;

    @ApiModelProperty(value = "The contracted value (R$).")
    private double contractedValue;

    @ApiModelProperty(value = "The contracted date.")
    private Date contractedDate;

    @ApiModelProperty(value = "The update date.")
    private Date modified;

    @ApiModelProperty(value = "The status.")
    private BankOperationStatus status;

}
