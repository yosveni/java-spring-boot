package com.linkapital.core.services.company.contract.enums;

public enum CompanyState {
    READY,
    PROSPECT_CONTACT,
    INTERESTED,
    NEGOTIATION,
    CLIENT;

    public static CompanyState getValueOf(int value) {
        return switch (value) {
            case 0 -> READY;
            case 1 -> PROSPECT_CONTACT;
            case 2 -> INTERESTED;
            case 3 -> NEGOTIATION;
            default -> CLIENT;
        };
    }
}
