package com.linkapital.core.services.interview.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The answer interview data.")
@Getter
@Setter
public class CreateAnswerInterviewTO {

    @ApiModelProperty(value = "The question id.", required = true)
    @NotNull
    @Min(1)
    private long questionId;

    @ApiModelProperty(value = "The answer value.", required = true)
    @NotNull
    private int answerValue;

}
