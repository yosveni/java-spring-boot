package com.linkapital.core.services.indicative_offer.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(value = "The offer volume data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndicativeVolumeTO {

    @ApiModelProperty(value = "The minimum volume")
    private double volumeMin;

    @ApiModelProperty(value = "The maximum volume")
    private double volumeMax;

    public IndicativeVolumeTO withVolumeMin(double volumeMin) {
        setVolumeMin(volumeMin);
        return this;
    }

    public IndicativeVolumeTO withVolumeMax(double volumeMax) {
        setVolumeMax(volumeMax);
        return this;
    }

}
