package com.linkapital.core.services.shared.contract.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Learned {
    AVAL_SOCIOS("Aval de socios"),
    INMUEBLES("Inmuebles"),
    DEMOSTRACIONES_RESULTADOS("Demostraciones de resultados"),
    RECEITA_RECURRENTE("Receita recurrente");

    @Getter
    private final String value;

    public static String getLearned(int type) {
        return switch (type) {
            case 1 -> AVAL_SOCIOS.value;
            case 2 -> INMUEBLES.value;
            case 3 -> DEMOSTRACIONES_RESULTADOS.value;
            default -> RECEITA_RECURRENTE.value;
        };
    }
}
