package com.linkapital.core.services.indicative_offer.contract.to.update;

import com.linkapital.core.services.indicative_offer.contract.to.IndicativeOfferTaxTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "All data of the learning offer tow to be update.")
@Getter
@Setter
public class UpdateIndicativeOfferTwoTO extends UpdateIndicativeOfferTO {

    private static final long serialVersionUID = 8407671711309567100L;

    @ApiModelProperty(value = "Tax values of the offer")
    private IndicativeOfferTaxTO indicativeOfferTax;

}
