package com.linkapital.core.services.authorization.contract.to.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "The data to create the authorization question.")
@Getter
@Setter
public class CreateAuthorizationQuestionTO {

    @ApiModelProperty(value = "The question.", required = true)
    @NotEmpty
    private String question;

    @ApiModelProperty(value = "The title detail.")
    private String detailTitle;

    @ApiModelProperty(value = "The detail.")
    private String detail;

}
