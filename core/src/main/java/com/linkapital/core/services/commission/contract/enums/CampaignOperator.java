package com.linkapital.core.services.commission.contract.enums;

public enum CampaignOperator {
    EQUAL("=="),
    GREATER_OR_EQUAL(">="),
    LESSER_OR_EQUAL("<="),
    LESSER("<"),
    GREATER(">");

    private final String value;

    CampaignOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
