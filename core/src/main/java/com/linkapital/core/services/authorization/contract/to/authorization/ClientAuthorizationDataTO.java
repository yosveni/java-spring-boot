package com.linkapital.core.services.authorization.contract.to.authorization;

import com.linkapital.core.services.authorization.contract.enums.AuthorizationState;
import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "The owner authorization data.")
@Getter
@Setter
public class ClientAuthorizationDataTO extends BaseOwnerAuthorizationTO {

    @ApiModelProperty(value = "The token.")
    private String token;

    @ApiModelProperty(value = "The company cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "The company social reason.")
    private String socialReason;

    @ApiModelProperty(value = "The agent name.")
    private String agentName;

    @ApiModelProperty(value = "The agent cpf.")
    private String agentCpf;

    @ApiModelProperty(value = "The authorization state.")
    private AuthorizationState state;

    @ApiModelProperty(value = "The questions list.")
    private List<AuthorizationQuestionTO> questions;

    public ClientAuthorizationDataTO() {
        this.questions = new ArrayList<>();
    }

    public ClientAuthorizationDataTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public ClientAuthorizationDataTO withSocialReason(String socialReason) {
        setSocialReason(socialReason);
        return this;
    }

    public ClientAuthorizationDataTO withAgentName(String agentName) {
        setAgentName(agentName);
        return this;
    }

    public ClientAuthorizationDataTO withAgentCpf(String agentCpf) {
        setAgentCpf(agentCpf);
        return this;
    }

    public ClientAuthorizationDataTO withQuestions(List<AuthorizationQuestionTO> questions) {
        setQuestions(questions);
        return this;
    }

}
