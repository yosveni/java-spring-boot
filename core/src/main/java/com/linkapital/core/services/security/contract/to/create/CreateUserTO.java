package com.linkapital.core.services.security.contract.to.create;

import com.linkapital.core.services.security.contract.to.RoleTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static java.util.Locale.ROOT;

@ApiModel(description = "All data needed to logout a user.")
@Getter
@Setter
public class CreateUserTO {

    @ApiModelProperty(value = "The email.", required = true)
    @NotEmpty
    @Email
    private String email;

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

    @ApiModelProperty(value = "The cpf.")
    @Size(max = 11)
    private String cpf;

    @ApiModelProperty(value = "The role.", required = true)
    @NotNull
    private RoleTO role;

    public String getEmail() {
        return email.toLowerCase(ROOT);
    }

}