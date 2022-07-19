package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.comment.contract.enums.LearningSession;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "The data to confirm session of learner.")
@Getter
@Setter
public class LearningSessionConfirmTO {

    @ApiModelProperty(value = "The cnpj.", required = true)
    @NotEmpty
    @Size(max = 18)
    private String cnpj;

    @ApiModelProperty(value = "The learning session.", required = true)
    @NotNull
    private LearningSession learningSession;

}
