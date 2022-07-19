package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The operational margin analysis data")
@Getter
@Setter
public class OperationalMarginTO {

    @ApiModelProperty(value = "The formula used to calculate the operational margin")
    private String moFormula;

    @ApiModelProperty(value = "The operational margin result")
    private String moResult;

    @ApiModelProperty(value = "The operational result value")
    private double operationalResult;

    @ApiModelProperty(value = "The liquid receipt value")
    private double liquidReceipt;

    public OperationalMarginTO init() {
        setMoFormula("Margem Operacional = Resultado Operacional / Receita L\u00EDquida");

        return this;
    }

    public OperationalMarginTO withMoResult(double moResult) {
        setMoResult(moResult + " %");
        return this;
    }

    public OperationalMarginTO withOperationalResult(double operationalResult) {
        setOperationalResult(operationalResult);
        return this;
    }

    public OperationalMarginTO withLiquidReceipt(double liquidReceipt) {
        setLiquidReceipt(liquidReceipt);
        return this;
    }

}
