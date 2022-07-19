package com.linkapital.core.services.invoice.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The invoice issued data.")
@Getter
@Setter
public class InvoiceIssuedTO {

    @ApiModelProperty(value = "The month")
    private String month;

    @ApiModelProperty(value = "The month number")
    private int monthNumber;

    @ApiModelProperty(value = "The year")
    private int year;

    @ApiModelProperty(value = "The total value")
    private double total;

    public InvoiceIssuedTO withMonth(String month) {
        setMonth(month);
        return this;
    }

    public InvoiceIssuedTO withMonthNumber(int monthNumber) {
        setMonthNumber(monthNumber);
        return this;
    }

    public InvoiceIssuedTO withYear(int year) {
        setYear(year);
        return this;
    }

    public InvoiceIssuedTO withTotal(double total) {
        setTotal(total);
        return this;
    }

}
