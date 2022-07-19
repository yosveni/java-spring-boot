package com.linkapital.core.services.industrial_production_index.contract.enums;

public enum PhysicalProductionVariableNames {
    MONTHLY_INDEX("\u00CDndice mensal (Base: igual m\u00EAs do ano anterior = 100)"),
    FIXED_BASE_INDEX_WITHOUT_SEASONAL_ADJUSTMENT("\u00CDndice de base fixa sem ajuste sazonal (Base: m\u00E9dia" +
            " de 2012 = 100)"),
    INDEX_ACCUMULATED_LAST_12_MONTHS("\u00CDndice acumulado nos \u00FAltimos 12 meses (Base: \u00FAltimos 12" +
            " meses anteriores = 100)"),
    MONTHLY_PERCENTAGE_CHANGE("Varia\u00E7\u00E3o percentual mensal (Base: igual m\u00EAs do ano anterior)"),
    PERCENTAGE_CHANGE_ACCUMULATED_YEAR("Varia\u00E7\u00E3o percentual acumulada no ano (Base: igual" +
            " per\u00EDodo do ano anterior)"),
    YEAR_TO_DATE_INDEX("\u00CDndice acumulado no ano (Base: igual per\u00EDodo do ano anterior = 100)"),
    PERCENTAGE_CHANGE_ACCUMULATED_LAST_12_MONTHS("Varia\u00E7\u00E3o percentual acumulada nos" +
            " \u00FAltimos 12 meses (Base: \u00FAltimos 12 meses anteriores)"),
    OTHER("");

    public final String value;

    PhysicalProductionVariableNames(String value) {
        this.value = value;
    }

    public static PhysicalProductionVariableNames fromString(String text) {
        for (PhysicalProductionVariableNames val : PhysicalProductionVariableNames.values())
            if (val.value.equalsIgnoreCase(text))
                return val;

        return OTHER;
    }

}
