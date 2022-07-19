package com.linkapital.core.services.security.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static java.util.Locale.ROOT;

@ApiModel(description = "All data needed to register a user.")
@Getter
@Setter
public class RegisterUserTO {

    @ApiModelProperty(value = "The email.", required = true)
    @NotEmpty
    @Email
    private String email;

    @ApiModelProperty(notes = "The password.", required = true)
    @NotEmpty
    private String password;

    @ApiModelProperty(value = "The name.", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "The code country phone.", required = true)
    @NotEmpty
    @Length(max = 6)
    private String codeCountryPhone;

    @ApiModelProperty(value = "The phone number.", required = true)
    @NotEmpty
    private String phone;

    @ApiModelProperty(value = "The user cpf.", required = true)
    @NotEmpty
    @Size(max = 11)
    private String cpf;

    @ApiModelProperty(value = "Indicates if the user is an entrepreneur.")
    private boolean partner;

    public String getEmail() {
        return email.toLowerCase(ROOT);
    }

}
