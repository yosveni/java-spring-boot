package com.linkapital.core.services.company.contract.enums;

import lombok.Getter;

@Getter
public enum EmitterType {
    OTHER("OTHER"),
    PGFN("PGFN"),
    TCU("TCU"),
    TST("TST"),
    FGTS("FGTS"),
    SIT("SIT");

    private final String value;

    EmitterType(String value) {
        this.value = value;
    }

}
