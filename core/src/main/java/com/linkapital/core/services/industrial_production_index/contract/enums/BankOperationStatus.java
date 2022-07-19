package com.linkapital.core.services.industrial_production_index.contract.enums;

public enum BankOperationStatus {
    ACTIVE,
    LIQUIDATED,
    OTHER;

    public static BankOperationStatus getStatus(String value) {
        return switch (value) {
            case "ATIVA" -> ACTIVE;
            case "LIQUIDADA" -> LIQUIDATED;
            default -> OTHER;
        };
    }
}
