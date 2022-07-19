package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The dupont analysis data")
@Getter
@Setter
public class DupontTO {

    @ApiModelProperty(value = "The formula used to calculate the LIQUID MARGIN")
    private String mlFormula;

    @ApiModelProperty(value = "The LIQUID MARGIN result.")
    private String mlResult;

    @ApiModelProperty(value = "The formula used to calculate the ASSET TURN")
    private String gaFormula;

    @ApiModelProperty(value = "The ASSET TURN result.")
    private String gaResult;

    @ApiModelProperty(value = "The formula used to calculate LEVERAGE")
    private String leverageFormula;

    @ApiModelProperty(value = "The LEVERAGE result.")
    private String leverageResult;

    @ApiModelProperty(value = "The formula used to calculate ROE")
    private String roeFormula;

    @ApiModelProperty(value = "The ROE result.")
    private String roeResult;

    @ApiModelProperty(value = "The liquid profit value")
    private double liquidProfit;

    @ApiModelProperty(value = "The liquid receipt value")
    private double liquidReceipt;

    @ApiModelProperty(value = "The liquid equity value")
    private double liquidEquity;

    @ApiModelProperty(value = "The active value")
    private double active;

    public DupontTO init() {
        setMlFormula("Margem L\u00EDquida = Lucro L\u00EDquido / Receita L\u00EDquida");
        setGaFormula("Giro do Ativo = Receita L\u00EDquida / Ativo");
        setLeverageFormula("Alavancagem = Ativo / Patrim\u00F4nio L\u00EDquido");
        setRoeFormula("Lucro sobre o Patrim\u00F4nio L\u00EDquido = Lucro L\u00EDquido / Patrim\u00F4nio L\u00EDquido");
        return this;
    }

    public DupontTO withMlResult(double mlResult) {
        setMlResult(mlResult + " %");
        return this;
    }

    public DupontTO withGaResult(double gaResult) {
        setGaResult(gaResult + " %");
        return this;
    }

    public DupontTO withLeverageResult(double leverageResult) {
        setLeverageResult(leverageResult + " %");
        return this;
    }

    public DupontTO withRoeResult(double roeResult) {
        setRoeResult(roeResult + " %");
        return this;
    }

    public DupontTO withLiquidProfit(double liquidProfit) {
        setLiquidProfit(liquidProfit);
        return this;
    }

    public DupontTO withLiquidReceipt(double liquidReceipt) {
        setLiquidReceipt(liquidReceipt);
        return this;
    }

    public DupontTO withLiquidEquity(double liquidEquity) {
        setLiquidEquity(liquidEquity);
        return this;
    }

    public DupontTO withActive(double active) {
        setActive(active);
        return this;
    }

}
