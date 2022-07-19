package com.linkapital.core.services.security.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "The refresh token data.")
@Getter
@Setter
public class RefreshTokenTO {

    @ApiModelProperty(value = "The token.")
    private String token;

    @ApiModelProperty(value = "The refreshToken.", required = true)
    @NotEmpty
    private String refreshToken;

    public RefreshTokenTO withToken(String token) {
        setToken(token);
        return this;
    }

    public RefreshTokenTO withRefreshToken(String refreshToken) {
        setRefreshToken(refreshToken);
        return this;
    }

}
