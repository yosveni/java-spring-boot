package com.linkapital.core.services.security.contract.to;

import com.linkapital.core.services.identification.contract.enums.IdentificationState;
import com.linkapital.core.services.security.contract.enums.Intensity;
import com.linkapital.core.services.shared.contract.to.AddressTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The user data.")
@Getter
@Setter
public class UserTO {

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

    @ApiModelProperty(value = "Linkeding contact url.")
    private String linkedingContact;

    @ApiModelProperty(value = "The user cpf.")
    private String cpf;

    @ApiModelProperty(value = "If is enabled.")
    private boolean enabled;

    @ApiModelProperty(value = "Indicates if the user has already rated the system")
    private boolean hasRating;

    @ApiModelProperty(value = "The data created.")
    private Date created;

    @ApiModelProperty(value = "The intensity.")
    private Intensity intensity;

    @ApiModelProperty(value = "The identification state.")
    private IdentificationState identificationState;

    @ApiModelProperty(value = "The role.")
    private RoleTO role;

    @ApiModelProperty(value = "The user guide.")
    private UserGuideTO userGuide;

    @ApiModelProperty(value = "The address.")
    private AddressTO address;

    @ApiModelProperty(value = "The image.")
    private byte[] image;

}
