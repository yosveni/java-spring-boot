package com.linkapital.core.services.protest.contract.to;

import com.linkapital.core.services.protest.contract.emuns.ProtestArea;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The Protests by area.")
@Getter
@Setter
public class ProtestAreaTO {

    @ApiModelProperty(value = "The percentage of the total represented by the protests of the area in reference.")
    private String percent;

    @ApiModelProperty(value = "The amount of protest according to the area in reference.")
    private int amount;

    @ApiModelProperty(value = "The total value of protests according to the area in reference.")
    private double value;

    @ApiModelProperty(value = "The area.")
    private ProtestArea area;

    public ProtestAreaTO withPercent(String percent) {
        setPercent(percent);
        return this;
    }

    public ProtestAreaTO withArea(ProtestArea area) {
        setArea(area);
        return this;
    }

    public ProtestAreaTO withAmount(int amount) {
        setAmount(amount);
        return this;
    }

    public ProtestAreaTO withValue(double value) {
        setValue(value);
        return this;
    }

}
