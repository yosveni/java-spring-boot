package com.linkapital.core.services.interview.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@ApiModel(description = "The question interview data.")
@Getter
@Setter
public class UpdateQuestionInterviewTO extends CreateQuestionInterviewTO {

    @ApiModelProperty(value = "The id.", required = true)
    @Min(1)
    private long id;

}
