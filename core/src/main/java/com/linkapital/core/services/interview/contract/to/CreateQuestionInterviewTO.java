package com.linkapital.core.services.interview.contract.to;

import com.linkapital.core.services.interview.contract.enums.InterviewArea;
import com.linkapital.core.services.interview.contract.enums.InterviewField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The question interview data.")
@Getter
@Setter
public class CreateQuestionInterviewTO {

    @ApiModelProperty(value = "The question.", required = true)
    @NotEmpty
    private String question;

    @ApiModelProperty(value = "The possible value.", required = true)
    @NotEmpty
    private String possibleValue;

    @ApiModelProperty(value = "The area.", required = true)
    @NotNull
    private InterviewArea area;

    @ApiModelProperty(value = "The field.", required = true)
    @NotNull
    private InterviewField field;

}
