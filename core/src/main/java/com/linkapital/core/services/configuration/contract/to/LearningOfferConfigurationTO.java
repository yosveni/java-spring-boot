package com.linkapital.core.services.configuration.contract.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LearningOfferConfigurationTO {

    @JsonProperty("deadline")
    private String deadline;

    @JsonProperty("guarantee_min_value")
    private long guaranteeMinValue;

    @JsonProperty("guarantee_max_value")
    private long guaranteeMaxValue;

    @JsonProperty("percent_volume")
    private long percentVolume;

    @JsonProperty("ipca")
    private double ipCa;

    @JsonProperty("tax")
    private double tax;

    @JsonProperty("min_tax")
    private double minTax;

    @JsonProperty("max_tax")
    private double maxTax;

}
