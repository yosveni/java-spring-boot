package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The Vertical analysis data.")
@Getter
@Setter
public class VerticalAnalysisTO extends SpedPatternTO {

    @ApiModelProperty(value = "The percentage with respect to the liquid recipe.")
    private String percent;

    public VerticalAnalysisTO withPercent(String percent) {
        setPercent(percent);
        return this;
    }

}
