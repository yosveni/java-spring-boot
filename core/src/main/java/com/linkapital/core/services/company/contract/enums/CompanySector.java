package com.linkapital.core.services.company.contract.enums;

public enum CompanySector {
    SERVICES,
    INDUSTRY,
    COMMERCE;

    public static CompanySector getCompanySector(String type) {
        return switch (type.toUpperCase()) {
            case "INDUSTRIA" -> INDUSTRY;
            case "COMERCIO" -> COMMERCE;
            default -> SERVICES;
        };
    }
}
