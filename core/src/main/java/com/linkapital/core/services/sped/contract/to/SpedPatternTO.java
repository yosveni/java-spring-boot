package com.linkapital.core.services.sped.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The sped balance data.")
@Getter
@Setter
public class SpedPatternTO {

    @ApiModelProperty(value = "The agglutination code.")
    private String code;

    @ApiModelProperty(value = "The agglutination code synthetic.")
    private String codeSynthetic;

    @ApiModelProperty(value = "The agglutination code description.")
    private String codeDescription;

    @ApiModelProperty(value = "The end value situation.")
    private String endValueSituation;

    @ApiModelProperty(value = "The agglutination code level.")
    private int codeLevel;

    @ApiModelProperty(value = "The end value.")
    private double endValue;

}
