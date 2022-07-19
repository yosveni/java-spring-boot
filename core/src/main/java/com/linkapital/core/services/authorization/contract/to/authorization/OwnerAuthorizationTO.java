package com.linkapital.core.services.authorization.contract.to.authorization;

import com.linkapital.core.services.authorization.contract.enums.AuthorizationState;
import com.linkapital.core.services.authorization.contract.to.answer.AuthorizationAnswerTO;
import com.linkapital.core.services.authorization.contract.to.trace.AuthorizationTraceTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "The owner authorization data.")
@Getter
@Setter
public class OwnerAuthorizationTO extends BaseOwnerAuthorizationTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The token.")
    private String token;

    @ApiModelProperty(value = "The reason.")
    private String cancelledReason;

    @ApiModelProperty(value = "The If has power to consult scr data.")
    private boolean consultScr;

    @ApiModelProperty(value = "If has power to represent the enterprise.")
    private boolean hasPower;

    @ApiModelProperty(value = "The authorization state.")
    private AuthorizationState state;

    @ApiModelProperty(value = "The data referring to the host where the authorization is made.")
    private AuthorizationTraceTO authorizationTrace;

    @ApiModelProperty(value = "The answers.")
    private List<AuthorizationAnswerTO> answers;

    public OwnerAuthorizationTO() {
        this.answers = new ArrayList<>();
    }

}
