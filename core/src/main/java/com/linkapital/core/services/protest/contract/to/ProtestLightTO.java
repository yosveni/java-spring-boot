package com.linkapital.core.services.protest.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@ApiModel(description = "The protest data.")
@Getter
@Setter
public class ProtestLightTO {

    @ApiModelProperty(value = "The assignor name.")
    private String assignorName;

    @ApiModelProperty(value = "The value.")
    private double value;

    @ApiModelProperty(value = "The consult date.")
    private LocalDate consultDate;

    public ProtestLightTO withAssignorName(String assignorName) {
        setAssignorName(assignorName);
        return this;
    }

    public ProtestLightTO withValue(double value) {
        setValue(value);
        return this;
    }

    public ProtestLightTO withConsultDate(LocalDate consultDate) {
        setConsultDate(consultDate);
        return this;
    }

}
