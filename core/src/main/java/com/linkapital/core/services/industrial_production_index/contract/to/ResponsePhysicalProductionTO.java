package com.linkapital.core.services.industrial_production_index.contract.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePhysicalProductionTO {

    @JsonProperty("NN")
    private String territorialLevel;

    @JsonProperty("MN")
    private String measureUnit;

    @JsonProperty("V")
    private String value;

    @JsonProperty("D2N")
    private String variable;

    @JsonProperty("D3N")
    private String date;

    @JsonProperty("D4N")
    private String codeDescription;

    public ResponsePhysicalProductionTO withDate(String date) {
        this.date = date;
        return this;
    }

}
