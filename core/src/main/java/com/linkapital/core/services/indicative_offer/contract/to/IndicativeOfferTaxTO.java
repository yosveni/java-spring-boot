package com.linkapital.core.services.indicative_offer.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(value = "The fiscal data of the indicative offer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndicativeOfferTaxTO {

    @ApiModelProperty(value = "The minimum offer tax")
    private double minOfferTax;

    @ApiModelProperty(value = "The maximum offer tax")
    private double maxOfferTax;

    public IndicativeOfferTaxTO withMinOfferTax(double minOfferTax) {
        setMinOfferTax(minOfferTax);
        return this;
    }

    public IndicativeOfferTaxTO withMaxOfferTax(double maxOfferTax) {
        setMaxOfferTax(maxOfferTax);
        return this;
    }

}
