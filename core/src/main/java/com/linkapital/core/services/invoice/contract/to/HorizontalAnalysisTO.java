package com.linkapital.core.services.invoice.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The horizontal analysis data.")
@Getter
@Setter
public class HorizontalAnalysisTO {

    @ApiModelProperty(value = "The invoice billing list.")
    private List<InvoiceIssuedTO> invoiceBilling;

    @ApiModelProperty(value = "The invoice payment list.")
    private List<InvoiceIssuedTO> invoicePayment;

    @ApiModelProperty(value = "The invoice tax list.")
    private List<InvoiceTaxTO> invoiceTax;

    @ApiModelProperty(value = "The invoice employees list.")
    private List<InvoiceEmployeeTO> invoiceEmployee;

    public HorizontalAnalysisTO() {
        this.invoiceBilling = new ArrayList<>();
        this.invoicePayment = new ArrayList<>();
        this.invoiceTax = new ArrayList<>();
        this.invoiceEmployee = new ArrayList<>();
    }

    public HorizontalAnalysisTO withInvoiceBilling(List<InvoiceIssuedTO> invoiceBilling) {
        setInvoiceBilling(invoiceBilling);
        return this;
    }

    public HorizontalAnalysisTO withInvoicePayment(List<InvoiceIssuedTO> invoicePayment) {
        setInvoicePayment(invoicePayment);
        return this;
    }

    public HorizontalAnalysisTO withInvoiceTax(List<InvoiceTaxTO> invoiceTax) {
        setInvoiceTax(invoiceTax);
        return this;
    }

    public HorizontalAnalysisTO withInvoiceEmployee(List<InvoiceEmployeeTO> invoiceEmployee) {
        setInvoiceEmployee(invoiceEmployee);
        return this;
    }

}
