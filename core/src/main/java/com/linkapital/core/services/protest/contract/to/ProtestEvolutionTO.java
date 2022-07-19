package com.linkapital.core.services.protest.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The protest evolution data.")
@Getter
@Setter
public class ProtestEvolutionTO {

    @ApiModelProperty(value = "The date of the evolution, can be month and year or only year.")
    private String date;

    @ApiModelProperty(value = "The total value of protests according to the month or year in reference.")
    private String value;

    @ApiModelProperty(value = "The percentage of increase or decrease of the value in correspondence to the previous" +
            " month or year depending on the date value.")
    private String percent;

    public ProtestEvolutionTO withDate(String date) {
        setDate(date);
        return this;
    }

    public ProtestEvolutionTO withValue(String value) {
        setValue(value);
        return this;
    }

    public ProtestEvolutionTO withPercent(String percent) {
        setPercent(percent);
        return this;
    }

}
