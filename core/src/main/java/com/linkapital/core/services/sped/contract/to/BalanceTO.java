package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The bank analysis data.")
@Getter
@Setter
public class BalanceTO {

    @ApiModelProperty(value = "The formula used to calculate CCL.")
    private String cclFormula;

    @ApiModelProperty(value = "The formula used to calculate DL.")
    private String dlFormula;

    @ApiModelProperty(value = "The formula used to calculate DLNO.")
    private String dlNoFormula;

    @ApiModelProperty(value = "The formula used to calculate DLRO.")
    private String dlRoFormula;

    @ApiModelProperty(value = "The Liquid Debt with result of the operation.")
    private String dlRoResult;

    @ApiModelProperty(value = "The formula used to calculate DLRNO.")
    private String dlRoNoFormula;

    @ApiModelProperty(value = "The Liquid Debt with operating income, after a new operation.")
    private String dlRoNoResult;

    @ApiModelProperty(value = "The Liquid working capital result.")
    private double cclResult;

    @ApiModelProperty(value = "The Liquid Debt result.")
    private double dlResult;

    @ApiModelProperty(value = "The Liquid Debt after a new trade.")
    private double dlNoResult;

    @ApiModelProperty(value = "The current assets value.")
    private double currentAssets;

    @ApiModelProperty(value = "The current liabilities value.")
    private double currentLiabilities;

    @ApiModelProperty(value = "The short term debt value.")
    private double shortTermDebt;

    @ApiModelProperty(value = "The long term debt value.")
    private double longTermDebt;

    @ApiModelProperty(value = "The availabilities value.")
    private double availabilities;

    @ApiModelProperty(value = "The operation value.")
    private double operationValue;

    @ApiModelProperty(value = "The operational result value.")
    private double operationalResult;

    public BalanceTO init() {
        setCclFormula("Capital Circulante L\u00EDquido = Ativo Circulante - Passivo Circulante");
        setDlFormula("D\u00EDvida L\u00EDquida = D\u00EDvidas de Curto Prazo + D\u00EDvidas de Longo Prazo - " +
                "Disponibilidades");
        setDlNoFormula("D\u00EDvida L\u00EDquida Ap\u00F3s Nova Opera\u00E7\u00E3o = D\u00EDvida L\u00EDquida + " +
                "Valor da Opera\u00E7\u00E3o");
        setDlRoFormula("D\u00EDvida L\u00EDquida / Resultado Operacional");
        setDlRoNoFormula("D\u00EDvida L\u00EDquida Ap\u00F3s Nova Opera\u00E7\u00E3o / Resultado Operacional");

        return this;
    }

    public BalanceTO withDlResult(double dlResult) {
        setDlResult(dlResult);
        return this;
    }

    public BalanceTO withDlNoResult(double dlNoResult) {
        setDlNoResult(dlNoResult);
        return this;
    }

    public BalanceTO withDlRoResult(double dlRoResult) {
        setDlRoResult(dlRoResult + " %");
        return this;
    }

    public BalanceTO withDlRoNoResult(double dlRoNoResult) {
        setDlRoNoResult(dlRoNoResult + " %");
        return this;
    }

    public BalanceTO withCclResult(double cclResult) {
        setCclResult(cclResult);
        return this;
    }

    public BalanceTO withCurrentAssets(double currentAssets) {
        setCurrentAssets(currentAssets);
        return this;
    }

    public BalanceTO withCurrentLiabilities(double currentLiabilities) {
        setCurrentLiabilities(currentLiabilities);
        return this;
    }

    public BalanceTO withShortTermDebt(double shortTermDebt) {
        setShortTermDebt(shortTermDebt);
        return this;
    }

    public BalanceTO withLongTermDebt(double longTermDebt) {
        setLongTermDebt(longTermDebt);
        return this;
    }

    public BalanceTO withAvailabilities(double availabilities) {
        setAvailabilities(availabilities);
        return this;
    }

    public BalanceTO withOperationValue(double operationValue) {
        setOperationValue(operationValue);
        return this;
    }

    public BalanceTO withOperationalResult(double operationalResult) {
        setOperationalResult(operationalResult);
        return this;
    }

}
