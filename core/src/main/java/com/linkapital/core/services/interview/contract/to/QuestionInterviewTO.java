package com.linkapital.core.services.interview.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@ApiModel(description = "The question interview data.")
@Getter
@Setter
public class QuestionInterviewTO extends CreateQuestionInterviewTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The created date.")
    private LocalDateTime created;

}
