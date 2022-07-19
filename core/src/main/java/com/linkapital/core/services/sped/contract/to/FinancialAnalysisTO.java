package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The Financial Analysis data.")
@Getter
@Setter
public class FinancialAnalysisTO {

    @ApiModelProperty(value = "The dupont analysis data.")
    private DupontTO dupont;

    @ApiModelProperty(value = "The cash conversion analysis data.")
    private CashConversionTO cashConversion;

    @ApiModelProperty(value = "The operational margin analysis data.")
    private OperationalMarginTO operationalMargin;

    @ApiModelProperty(value = "The bank analysis data.")
    private BalanceTO balance;

    public FinancialAnalysisTO withDupont(DupontTO dupont) {
        setDupont(dupont);
        return this;
    }

    public FinancialAnalysisTO withCashConversion(CashConversionTO cashConversion) {
        setCashConversion(cashConversion);
        return this;
    }

    public FinancialAnalysisTO withOperationalMargin(OperationalMarginTO operationalMargin) {
        setOperationalMargin(operationalMargin);
        return this;
    }

    public FinancialAnalysisTO withBalance(BalanceTO balance) {
        setBalance(balance);
        return this;
    }

}
