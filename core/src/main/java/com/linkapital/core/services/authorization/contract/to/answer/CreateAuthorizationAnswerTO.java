package com.linkapital.core.services.authorization.contract.to.answer;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The data to create Authorization Answer.")
@Getter
@Setter
public class CreateAuthorizationAnswerTO {

    @ApiModelProperty(value = "The id of the authorization question.", required = true)
    @NotNull
    private long questionId;

    @ApiModelProperty(value = "The positive answer.", required = true)
    @NotNull
    private boolean answer;

}
