package com.linkapital.core.services.property_guarantee.contract.to;

import com.linkapital.core.services.property_guarantee.contract.enums.OwnerType;
import com.linkapital.core.services.property_guarantee.contract.enums.PropertyType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BasePropertyGuaranteeTO {

    @ApiModelProperty(value = "The property registration number registered with the real estate registry.")
    private String registryNumber;

    @ApiModelProperty(value = "The reference property.", required = true)
    private long referenceProperty;

    @ApiModelProperty(value = "The valuation value of the property, according to the property registration document.")
    @Min(0)
    private double evaluationValue;

    @ApiModelProperty(value = "The built area, according to the property registration document.")
    @Min(0)
    private double builtArea;

    @ApiModelProperty(value = "The land area, according to the property registration document.")
    @Min(0)
    private double groundArea;

    @ApiModelProperty(value = "The property type.", required = true)
    @NotNull
    private PropertyType type;

    @ApiModelProperty(value = "The type of property owner.", required = true)
    @NotNull
    private OwnerType ownerType;

}
