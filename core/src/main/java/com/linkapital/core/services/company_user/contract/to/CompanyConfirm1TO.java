package com.linkapital.core.services.company_user.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The data to confirm information of learner 1.")
@Getter
@Setter
public class CompanyConfirm1TO extends LearningConfirmTO {

    @ApiModelProperty(value = "Indicates if the status of the registration form is the latest version.", required = true)
    @NotNull
    private boolean latestRegistrationForm;

}
