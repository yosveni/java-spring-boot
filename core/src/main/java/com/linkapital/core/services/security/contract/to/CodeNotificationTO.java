package com.linkapital.core.services.security.contract.to;

import com.linkapital.core.services.security.contract.enums.NotificationProcessType;
import com.linkapital.core.services.security.contract.enums.NotificationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static java.util.Locale.ROOT;

@ApiModel(description = "The data to send the notification code.")
@Setter
public class CodeNotificationTO {

    @ApiModelProperty(value = "The email.")
    @Email
    private String email;

    @ApiModelProperty(value = "The code country phone.")
    private String codeCountryPhone;

    @ApiModelProperty(value = "The phone.")
    private String phone;

    @ApiModelProperty(value = "The notification type.", required = true)
    @NotNull
    private NotificationType type;

    @ApiModelProperty(value = "The process type.")
    private NotificationProcessType processType;

    public String getEmail() {
        return email == null
                ? "anonymous"
                : email.toLowerCase(ROOT);
    }

    public String getPhone() {
        return this.codeCountryPhone == null
                ? this.phone
                : this.codeCountryPhone + this.phone;
    }

    public NotificationType getType() {
        return this.type;
    }

    public NotificationProcessType getProcessType() {
        return this.processType;
    }

    public String getCodeCountryPhone() {
        return codeCountryPhone;
    }

}
