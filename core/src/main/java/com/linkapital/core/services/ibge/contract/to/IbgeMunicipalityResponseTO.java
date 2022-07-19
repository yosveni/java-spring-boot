package com.linkapital.core.services.ibge.contract.to;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IbgeMunicipalityResponseTO {

    private long id;
    private String nome;
    private Object microrregiao;
    private Object regiaoImediata;

}
