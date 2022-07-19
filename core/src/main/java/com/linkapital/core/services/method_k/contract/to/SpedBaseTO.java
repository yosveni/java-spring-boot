package com.linkapital.core.services.method_k.contract.to;

import com.linkapital.core.services.method_k.contract.enums.KeyDescriptionSped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The score operation data.")
@Getter
@Setter
public class SpedBaseTO {

    @ApiModelProperty(value = "The SPED year.")
    private int year;

    @ApiModelProperty(value = "The end value.")
    private double endValue;

    @ApiModelProperty(value = "The code description.")
    private KeyDescriptionSped codeDescription;

    public SpedBaseTO withYear(int year) {
        setYear(year);
        return this;
    }

    public SpedBaseTO withEndValue(double value) {
        setEndValue(value);
        return this;
    }

    public SpedBaseTO withCodeDescription(KeyDescriptionSped codeName) {
        setCodeDescription(codeName);
        return this;
    }

}
