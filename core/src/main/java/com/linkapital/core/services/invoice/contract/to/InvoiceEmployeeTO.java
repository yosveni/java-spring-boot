package com.linkapital.core.services.invoice.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The invoice employee data.")
@Getter
@Setter
public class InvoiceEmployeeTO {

    @ApiModelProperty(value = "The year.")
    private int year;

    @ApiModelProperty(value = "The growth count.")
    private int employeeGrowth;

    @ApiModelProperty(value = "The growth number.")
    private double growth;

    public InvoiceEmployeeTO withYear(int year) {
        setYear(year);
        return this;
    }

    public InvoiceEmployeeTO withEmployeeGrowth(int employeeGrowth) {
        setEmployeeGrowth(employeeGrowth);
        return this;
    }

    public InvoiceEmployeeTO withGrowth(double growth) {
        setGrowth(growth);
        return this;
    }

}
