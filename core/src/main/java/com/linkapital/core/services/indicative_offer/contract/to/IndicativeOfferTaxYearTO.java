package com.linkapital.core.services.indicative_offer.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(value = "The annual tax data of the indicative offer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndicativeOfferTaxYearTO {

    @ApiModelProperty(value = "The minimum offer tax per year")
    private double minOfferTaxYear;

    @ApiModelProperty(value = "The maximum offer tax per year")
    private double maxOfferTaxYear;

    public IndicativeOfferTaxYearTO withMinOfferTaxYear(double minOfferTaxYear) {
        setMinOfferTaxYear(minOfferTaxYear);
        return this;
    }

    public IndicativeOfferTaxYearTO withMaxOfferTaxYear(double maxOfferTaxYear) {
        setMaxOfferTaxYear(maxOfferTaxYear);
        return this;
    }

}
