package com.linkapital.core.services.commission.contract.to.get;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The commission user.")
@Getter
@Setter
public class CommissionUserTO extends CommissionTO {

    @ApiModelProperty(value = "The cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "The social reason.")
    private String socialReason;

    @ApiModelProperty(value = "The offer type.")
    private int offerType;

    public CommissionUserTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public CommissionUserTO withSocialReason(String socialReason) {
        setSocialReason(socialReason);
        return this;
    }

    public CommissionUserTO withOfferType(int type) {
        setOfferType(type);
        return this;
    }

}
