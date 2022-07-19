package com.linkapital.core.services.authorization.contract.to.answer;

import com.linkapital.core.services.authorization.contract.to.trace.CreateAuthorizationTraceTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The data to create Authorization by K agent.")
@Getter
@Setter
public class AuthorizationAnswerForAgentTO {

    @ApiModelProperty(value = "The owner authorization token.", required = true)
    @NotEmpty
    private String token;

    @ApiModelProperty(value = "If you grant permissions to SCR query.", required = true)
    private boolean consultScr;

    @ApiModelProperty(value = "Indicates if the agent is granted k powers to sign contracts on the company.", required = true)
    private boolean hasPower;

    @ApiModelProperty(value = "Indicates if the agent is granted k representative powers over the company.", required = true)
    private boolean hasRepresentativePower;

    @ApiModelProperty(value = "Indicates if the agent belongs to the company.", required = true)
    private boolean belongsCompany;

    @ApiModelProperty(value = "The data referring to the host where the authorization is made.", required = true)
    @NotNull
    private CreateAuthorizationTraceTO authorizationTrace;

    @ApiModelProperty(value = "The answers list.")
    private List<CreateAuthorizationAnswerTO> answers;

    public AuthorizationAnswerForAgentTO() {
        this.answers = new ArrayList<>();
    }

}
