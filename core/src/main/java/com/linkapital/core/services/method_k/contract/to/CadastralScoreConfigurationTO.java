package com.linkapital.core.services.method_k.contract.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastralScoreConfigurationTO {

    @JsonProperty("best")
    private double best;

    @JsonProperty("worse")
    private double worse;

    @JsonProperty("scoreMax")
    private double scoreMax;

    @JsonProperty("point")
    private int point;

}
