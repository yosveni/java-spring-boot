package com.linkapital.core.services.company.contract.enums;

import static java.util.Locale.ROOT;

public enum CompanySize {
    EPP,
    ME,
    BIGGER;

    public static CompanySize getCompanySize(String type) {
        return switch (type.toUpperCase(ROOT)) {
            case "ME" -> ME;
            case "EPP" -> EPP;
            default -> BIGGER;
        };
    }
}
