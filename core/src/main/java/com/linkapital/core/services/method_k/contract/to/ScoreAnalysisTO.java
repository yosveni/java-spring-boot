package com.linkapital.core.services.method_k.contract.to;

import com.linkapital.core.services.method_k.contract.enums.ScoreType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The score analysis data.")
@Getter
@Setter
public class ScoreAnalysisTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The period of the year under analysis.")
    private int year;

    @ApiModelProperty(value = "The total value.")
    private double total;

    @ApiModelProperty(value = "The score type.")
    private ScoreType type;

    @ApiModelProperty(value = "The operations.")
    private List<ScoreOperationTO> operations;

    public ScoreAnalysisTO() {
        this.operations = new ArrayList<>();
    }

}
