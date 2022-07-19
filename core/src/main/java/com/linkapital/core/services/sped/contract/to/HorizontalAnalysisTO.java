package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The Horizontal Analysis data.")
@Getter
@Setter
public class HorizontalAnalysisTO extends SpedPatternTO {

    @ApiModelProperty(value = "The percentage current period.")
    private String percentCurrentPeriod;

    @ApiModelProperty(value = "The percentage previous period.")
    private String percentPreviousPeriod;

    public HorizontalAnalysisTO withPercentCurrentPeriod(String percentCurrentPeriod) {
        setPercentCurrentPeriod(percentCurrentPeriod);
        return this;
    }

    public HorizontalAnalysisTO withPercentPreviousPeriod(String percentPreviousPeriod) {
        setPercentPreviousPeriod(percentPreviousPeriod);
        return this;
    }

}
