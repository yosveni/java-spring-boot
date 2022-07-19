package com.linkapital.core.services.company.contract.enums;

public enum RegistrationSituation {
    ACTIVE,
    SUSPENDED,
    CLOSED,
    NULL,
    INAPT;

    public static RegistrationSituation getRegistrationSituation(String type) {
        return switch (type.toUpperCase()) {
            case "ATIVA" -> RegistrationSituation.ACTIVE;
            case "SUSPENSA" -> RegistrationSituation.SUSPENDED;
            case "BAIXADA" -> RegistrationSituation.CLOSED;
            case "NULA" -> RegistrationSituation.NULL;
            default -> RegistrationSituation.INAPT;
        };
    }
}
