package com.linkapital.core.services.company.contract.enums;

public enum CompanyClosingPropensity {
    VERY_LOW("MUITO BAIXA"),
    LOW("BAIXA"),
    MEDIUM("MEDIA"),
    HIGH("ALTA"),
    VERY_HIGH("MUITO ALTA");

    public final String value;

    CompanyClosingPropensity(String value) {
        this.value = value;
    }

    public static CompanyClosingPropensity getPropensity(String value) {
        for (CompanyClosingPropensity val : CompanyClosingPropensity.values())
            if (val.value.equalsIgnoreCase(value))
                return val;

        return null;
    }
}
