package com.linkapital.core.services.company_user.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "The base data to learning confirm.")
@Getter
@Setter
public class LearningConfirmTO {

    @ApiModelProperty(value = "The cnpj.", required = true)
    @NotEmpty
    private String cnpj;

    @ApiModelProperty(value = "The user id.")
    private Long userId;

}
