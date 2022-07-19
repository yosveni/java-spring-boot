package com.linkapital.core.services.property_guarantee.contract.to;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.property_guarantee.contract.enums.OwnerType;
import com.linkapital.core.services.property_guarantee.contract.enums.PropertyType;
import com.linkapital.core.services.shared.contract.to.AddressTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The property data in guarantee.")
@Getter
@Setter
public class PropertyGuaranteeTO extends BasePropertyGuaranteeTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The address.")
    private AddressTO address;

    @ApiModelProperty(value = "The registration document.")
    private DirectoryTO document;

    public PropertyGuaranteeTO withType(PropertyType type) {
        setType(type);
        return this;
    }

    public PropertyGuaranteeTO withOwnerType(OwnerType ownerType) {
        setOwnerType(ownerType);
        return this;
    }

}
