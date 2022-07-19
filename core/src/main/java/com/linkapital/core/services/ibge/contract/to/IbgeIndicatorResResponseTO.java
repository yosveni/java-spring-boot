package com.linkapital.core.services.ibge.contract.to;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class IbgeIndicatorResResponseTO {

    private String localidade;
    private Map<String, String> res;
    private Map<String, String> notas;

}
