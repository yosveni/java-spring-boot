package com.linkapital.core.services.invoice.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The vertical analysis data.")
@Getter
@Setter
public class VerticalAnalysisTO {

    @ApiModelProperty("The invoice billing list.")
    private List<InvoiceVerticalTO> invoiceBilling;

    @ApiModelProperty("The invoice payment list.")
    private List<InvoiceVerticalTO> invoicePayment;

    public VerticalAnalysisTO() {
        this.invoiceBilling = new ArrayList<>();
        this.invoicePayment = new ArrayList<>();
    }

    public VerticalAnalysisTO withInvoiceBilling(List<InvoiceVerticalTO> invoiceBilling) {
        setInvoiceBilling(invoiceBilling);
        return this;
    }

    public VerticalAnalysisTO withInvoicePayment(List<InvoiceVerticalTO> invoicePayment) {
        setInvoicePayment(invoicePayment);
        return this;
    }

}
