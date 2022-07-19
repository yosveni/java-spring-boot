package com.linkapital.core.services.method_k.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Getter
@Setter
public class ScoreCodes {

    public static final String nameOperationOne = "\u00CDndices de Endividamento";
    public static final String nameOperationTwo = "\u00CDndices de Liquidez";
    public static final String nameOperationThree = "\u00CDndices de Rentabilidade";
    public static final String nameOperationFour = "\u00CDndices de Porte";
    public static final String nameOperationFive = "\u00CDndices de Despesas";
    public static final String nameOperationSix = "\u00CDndices de Atividade";
    public static final String cndTextOne = "Certid\u00E3o positiva de d\u00E9bitos emitida";
    public static final String cndTextTwo = "N\u00E3o \u00E9 poss\u00EDvel emitir certid\u00E3o";
    public static final String cndTextThree = "Informa\u00E7\u00F5es insuficientes";
    //Keys for the register score
    public static final String pgfnInvoicingInformed = "D\u00EDvida PGFN por faturamento informado";
    public static final String amountProtestsByAge = "Quantidade de protestos por idade";
    public static final String valueTotalProtestsInvoicing = "Quantidade de protestos por faturamento";
    public static final String workProcessAge = "Quantidade de processos trabalhistas";
    public static final String totalValueProcessInvoicing = "Valor de los processos faturados";
    public static final String valueCertificates = "Valor de las certid\u00F5es";
    public static final String bdnsOperation = "Opera\u00E7\u00F5es com BDNS";
    public static final String debentureDebentureValue = "Debentures";
    public static final String criValue = "CRI";
    public static final String craValue = "CRA";
    public static final String pointInterview = "Pontos na entrevista";
    //Keys for the habitual score
    public static final String debtBndesCount = "Quantidade de d\u00EDvida com BNDES";
    public static final String debtBndesVol = "Volume de d\u00EDvida com BNDES";
    public static final String debtBndesCountLiqui = "Quantidade de d\u00EDvida com BNDES liquidada";
    public static final String debtBndesVolLiqui = "Volume de d\u00EDvida com BNDES liquidada";
    public static final String currentVolRawScore = "Volume de d\u00EDvida com BNDES atual";
    public static final String countOperBndes = "Quantidade de opera\u00E7\u00F5es com BNDES";
    public static final String debtVolByPl = "Volume por PL";
    public static final String debtUp90day = "D\u00E9bito ate 90 days";

    public static final List<String> codes = asList(
            "v205",
            "v210",
            "v220",
            "v230"
    );
    public static final Map<String, Integer> codeMaxDay = new HashMap<>() {
        {
            put("v205", 14);
            put("v210", 30);
            put("v220", 60);
            put("v230", 90);
        }
    };

    private ScoreCodes() {
    }

}

