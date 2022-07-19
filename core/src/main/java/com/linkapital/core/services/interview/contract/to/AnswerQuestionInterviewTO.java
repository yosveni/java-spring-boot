package com.linkapital.core.services.interview.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "The answer question interview data.")
@Getter
@Setter
public class AnswerQuestionInterviewTO {

    @ApiModelProperty(value = "The cnpj.", required = true)
    @NotEmpty
    private String cnpj;

    @ApiModelProperty(value = "The user id.")
    @Min(1)
    private long userId;

    @ApiModelProperty(value = "The answers.")
    @NotEmpty
    private List<CreateAnswerInterviewTO> answers;

    public AnswerQuestionInterviewTO() {
        this.answers = new ArrayList<>();
    }

}
