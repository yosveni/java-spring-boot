package com.linkapital.core.services.property_guarantee.contract.to;

import com.linkapital.core.services.shared.contract.to.UpdateAddressTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The property data in guarantee.")
@Getter
@Setter
public class UpdatePropertyGuaranteeTO extends BasePropertyGuaranteeTO {

    @ApiModelProperty(value = "The id.", required = true)
    @Min(1)
    private long id;

    @ApiModelProperty(value = "The address.", required = true)
    @NotNull
    private UpdateAddressTO address;

}
