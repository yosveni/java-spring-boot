package com.linkapital.core.services.company.contract.enums;

public enum EnabledSituation {
    DEFERRED,
    DEFERRED_CANCELED,
    DISABLED,
    REJECTED,
    SUSPENDED,
    NO_INFORMATION;

    public static EnabledSituation getEnabledSituation(String type) {
        return switch (type) {
            case "DEFERIDA" -> DEFERRED;
            case "DEFERIDA CANCELADA" -> DEFERRED_CANCELED;
            case "DESABILITADA" -> DISABLED;
            case "INDEFERIDA" -> REJECTED;
            case "SUSPENSA" -> SUSPENDED;
            default -> NO_INFORMATION;
        };
    }
}
