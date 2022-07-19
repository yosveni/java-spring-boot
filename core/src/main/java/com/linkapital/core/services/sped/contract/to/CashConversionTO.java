package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The cash conversion analysis data")
@Getter
@Setter
public class CashConversionTO {

    private static final String days = " dias";

    @ApiModelProperty(value = "The formula used to calculate the average age of stock")
    private String imeFormula;

    @ApiModelProperty(value = "The average age of stock result")
    private String imeResult;

    @ApiModelProperty(value = "The formula used to calculate the average receipt deadline")
    private String pmrFormula;

    @ApiModelProperty(value = "The average receipt deadline result")
    private String pmrResult;

    @ApiModelProperty(value = "The formula used to calculate the average payment period")
    private String pmpFormula;

    @ApiModelProperty(value = "The average payment period result")
    private String pmpResult;

    @ApiModelProperty(value = "The formula used to calculate the purchases")
    private String purchaseFormula;

    @ApiModelProperty(value = "The formula used to calculate the cash conversion cycle")
    private String cccFormula;

    @ApiModelProperty(value = "The cash conversion cycle result")
    private String cccResult;

    @ApiModelProperty(value = "The stock value")
    private double stock;

    @ApiModelProperty(value = "The cost of boods sold value")
    private double cmv;

    @ApiModelProperty(value = "The bills to receive value")
    private double billsToReceive;

    @ApiModelProperty(value = "The gross receipt value")
    private double grossReceipt;

    @ApiModelProperty(value = "The accounts payable value")
    private double accountsPayable;

    @ApiModelProperty(value = "The purchases value")
    private double purchases;

    public CashConversionTO init() {
        setImeFormula("Idade M\u00E9dia do Estoque = (Estoque / Custo da Mercadoria Vendida) * Dias");
        setPmrFormula("Prazo M\u00E9dio de Recebimento = (Contas a Receber / Receita) * Dias");
        setPmpFormula("Prazo M\u00E9dio de Pagamento = (Contas a Pagar / Compras) * Dias");
        setPurchaseFormula("Compras = Custo da Mercadoria Vendida - Estoque");
        setCccFormula("Ciclo de Convers\u00E3o de Caixa = Idade M\u00E9dia do Estoque (IME) +" +
                " Prazo M\u00E9dio de Recebimento (PMR) - Prazo M\u00E9dio de Pagamento (PMP)");

        return this;
    }

    public CashConversionTO withImeResult(int imeResult) {
        setImeResult(imeResult + days);
        return this;
    }

    public CashConversionTO withPmrResult(int pmrResult) {
        setPmrResult(pmrResult + days);
        return this;
    }

    public CashConversionTO withPmpResult(int pmpResult) {
        setPmpResult(pmpResult + days);
        return this;
    }

    public CashConversionTO withCccResult(int cccResult) {
        setCccResult(cccResult + days);
        return this;
    }

    public CashConversionTO withStock(double stock) {
        setStock(stock);
        return this;
    }

    public CashConversionTO withCmv(double cmv) {
        setCmv(cmv);
        return this;
    }

    public CashConversionTO withBillsToReceive(double billsToReceive) {
        setBillsToReceive(billsToReceive);
        return this;
    }

    public CashConversionTO withGrossReceipt(double grossReceipt) {
        setGrossReceipt(grossReceipt);
        return this;
    }

    public CashConversionTO withAccountsPayable(double accountsPayable) {
        setAccountsPayable(accountsPayable);
        return this;
    }

    public CashConversionTO withPurchases(double purchases) {
        setPurchases(purchases);
        return this;
    }

}
