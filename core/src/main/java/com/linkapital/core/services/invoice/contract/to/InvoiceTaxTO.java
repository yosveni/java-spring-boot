package com.linkapital.core.services.invoice.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The invoice tax data.")
@Getter
@Setter
public class InvoiceTaxTO {

    @ApiModelProperty(value = "The month.")
    private String month;

    @ApiModelProperty(value = "The year.")
    private int year;

    @ApiModelProperty(value = "The month number")
    private int monthNumber;

    @ApiModelProperty(value = "The ipi.")
    private double ipi;

    @ApiModelProperty(value = "The pis.")
    private double pis;

    @ApiModelProperty(value = "The cofins.")
    private double cofins;

    @ApiModelProperty(value = "The icms.")
    private double icms;

    public InvoiceTaxTO withMonth(String month) {
        setMonth(month);
        return this;
    }

    public InvoiceTaxTO withMonthNumber(int monthNumber) {
        setMonthNumber(monthNumber);
        return this;
    }

    public InvoiceTaxTO withYear(int year) {
        setYear(year);
        return this;
    }

    public InvoiceTaxTO withIpi(double ipi) {
        setIpi(ipi);
        return this;
    }

    public InvoiceTaxTO withPis(double pis) {
        setPis(pis);
        return this;
    }

    public InvoiceTaxTO withCofins(double cofins) {
        setCofins(cofins);
        return this;
    }

    public InvoiceTaxTO withIcms(double icms) {
        setIcms(icms);
        return this;
    }

}
