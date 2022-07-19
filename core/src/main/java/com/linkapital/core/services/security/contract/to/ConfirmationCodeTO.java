package com.linkapital.core.services.security.contract.to;

import com.linkapital.core.services.security.contract.enums.NotificationProcessType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static java.util.Locale.ROOT;

@ApiModel(description = "The data to confirm the creation of the user, by sending the code.")
@Setter
public class ConfirmationCodeTO {

    @ApiModelProperty(value = "The user email.")
    @Email
    private String email;

    @ApiModelProperty(value = "The user code.", required = true)
    @NotEmpty
    private String code;

    @ApiModelProperty(value = "The type of confirmation to perform.", required = true)
    @NotNull
    private NotificationProcessType type;

    public String getEmail() {
        return email == null
                ? "anonymous"
                : email.toLowerCase(ROOT);
    }

    public String getCode() {
        return code;
    }

    public NotificationProcessType getType() {
        return type;
    }

}
