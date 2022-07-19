package com.linkapital.core.services.security.contract.to;

import com.linkapital.core.services.identification.contract.enums.IdentificationState;
import com.linkapital.core.services.shared.contract.to.AddressTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The authenticated user data.")
@Getter
@Setter
public class UserAuthenticatedTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The email.")
    private String email;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The code country phone.")
    private String codeCountryPhone;

    @ApiModelProperty(value = "The phone number.")
    private String phone;

    @ApiModelProperty(value = "The token.")
    private String token;

    @ApiModelProperty(value = "The refresh token.")
    private String refreshToken;

    @ApiModelProperty(value = "The user cpf.")
    private String cpf;

    @ApiModelProperty(value = "Indicates if the user has already rated the system")
    private boolean hasRating;

    @ApiModelProperty(value = "The identification state.")
    private IdentificationState identificationState;

    @ApiModelProperty(value = "The role")
    private RoleTO role;

    @ApiModelProperty(value = "The user guide.")
    private UserGuideTO userGuide;

    @ApiModelProperty(value = "The address.")
    private AddressTO address;

    @ApiModelProperty(value = "The image.")
    private byte[] image;

    public UserAuthenticatedTO withToken(String token) {
        setToken(token);
        return this;
    }

    public UserAuthenticatedTO withRefreshToken(String refreshToken) {
        setRefreshToken(refreshToken);
        return this;
    }

}
