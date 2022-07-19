package com.linkapital.core.services.company.contract.enums;

public enum ActivityLevel {
    HIGH,
    HALF,
    LOW,
    VERY_LOW,
    INACTIVE;

    public static ActivityLevel getActivityLevel(String type) {
        return switch (type.toUpperCase()) {
            case "ALTA" -> HIGH;
            case "MEDIO" -> HALF;
            case "BAIXA" -> LOW;
            case "MUITO BAIXA" -> VERY_LOW;
            default -> INACTIVE;
        };
    }
}
