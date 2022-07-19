package com.linkapital.core.services.protest.contract.to.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ResponseProtestDataTO {

    private String documento_pesquisado;
    private int quantidade_titulos;
    private Map<String, List<ResponseProtestRegistryTO>> cartorios;

    public ResponseProtestDataTO() {
        this.cartorios = new HashMap<>();
    }

}
