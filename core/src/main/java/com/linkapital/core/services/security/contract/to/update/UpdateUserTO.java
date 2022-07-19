package com.linkapital.core.services.security.contract.to.update;

import com.linkapital.core.services.security.contract.to.RoleTO;
import com.linkapital.core.services.shared.contract.to.CreateAddressTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "All data needed to edit a user.")
@Getter
@Setter
public class UpdateUserTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    @Min(1)
    private long id;

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

    @ApiModelProperty(value = "The role", required = true)
    @NotNull
    private RoleTO role;

    @ApiModelProperty(value = "The address.")
    private CreateAddressTO address;

}