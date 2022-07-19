package com.linkapital.core.services.authorization.contract.to.question;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The Owner Authentication data.")
@Getter
@Setter
public class AuthorizationQuestionTO extends CreateAuthorizationQuestionTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private long id;

}
