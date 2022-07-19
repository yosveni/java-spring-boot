package com.linkapital.core.services.authorization.contract.to.answer;

import com.linkapital.core.services.authorization.contract.to.trace.CreateAuthorizationTraceTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The data to create Authorization by owner.")
@Getter
@Setter
public class AuthorizationAnswerForOwnerTO {

    @ApiModelProperty(value = "The company cnpj.", required = true)
    @NotEmpty
    private String cnpj;

    @ApiModelProperty(value = "If you grant permissions to SCR query.", required = true)
    private boolean consultScr;

    @ApiModelProperty(value = "Indicates if the agent is granted k powers to sign contracts on the company.", required = true)
    private boolean hasPower;

    @ApiModelProperty(value = "The data referring to the host where the authorization is made.", required = true)
    @NotNull
    private CreateAuthorizationTraceTO authorizationTrace;

}
