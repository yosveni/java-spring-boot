package com.linkapital.core.services.invoice.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The invoice vertical data.")
@Getter
@Setter
public class InvoiceVerticalTO {

    @ApiModelProperty(value = "The cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The total.")
    private double total;

    public InvoiceVerticalTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public InvoiceVerticalTO withName(String name) {
        setName(name);
        return this;
    }

    public InvoiceVerticalTO withTotal(double total) {
        setTotal(total);
        return this;
    }

}
