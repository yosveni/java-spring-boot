package com.linkapital.core.services.shared.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CompanyEmployees implements Serializable {

    private int quantidade;
    private int quantidadeGrupo;

    public CompanyEmployees withQuantidade(int quantity) {
        setQuantidade(quantity);
        return this;
    }

    public CompanyEmployees withQuantidadeGrupo(int quantityGroup) {
        setQuantidadeGrupo(quantityGroup);
        return this;
    }

}
