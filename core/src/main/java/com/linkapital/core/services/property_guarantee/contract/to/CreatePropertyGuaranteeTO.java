package com.linkapital.core.services.property_guarantee.contract.to;

import com.linkapital.core.services.shared.contract.to.CreateAddressTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The data of the property in guarantee.")
@Getter
@Setter
public class CreatePropertyGuaranteeTO extends BasePropertyGuaranteeTO {

    @ApiModelProperty(value = "The company cnpj.", required = true)
    @NotEmpty
    private String cnpj;

    @ApiModelProperty(value = "The address.", required = true)
    @NotNull
    private CreateAddressTO address;

}
