package com.linkapital.core.services.authorization.contract.to.answer;


import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The data to update Authorization by K agent.")
@Getter
@Setter
public class AuthorizationAnswerTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private long id;

    @ApiModelProperty(value = "The positive answer.")
    @NotNull
    private boolean yesAnswer;

    @ApiModelProperty(value = "The id of the authorization question.", required = true)
    @NotNull
    private AuthorizationQuestionTO question;

}
