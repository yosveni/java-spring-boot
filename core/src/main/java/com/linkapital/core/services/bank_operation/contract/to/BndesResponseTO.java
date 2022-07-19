package com.linkapital.core.services.bank_operation.contract.to;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BndesResponseTO {

    private String tope;
    private String dataContratacao;
    private double valorContratacao;
    private String[] uf;
    private String[] liquidada;
    private String[] produtoBndes;
    private String[] custoFinanceiro;
    private String[] taxaJuros;
    private String[] prazoCarencia;
    private String[] prazoAmortizacao;
    private String[] agenteFinanceiro;

}
