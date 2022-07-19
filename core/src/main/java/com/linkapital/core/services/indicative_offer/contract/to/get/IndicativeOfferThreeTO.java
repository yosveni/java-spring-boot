package com.linkapital.core.services.indicative_offer.contract.to.get;

import com.linkapital.core.services.indicative_offer.contract.to.IndicativeOfferTaxTO;
import com.linkapital.core.services.indicative_offer.contract.to.IndicativeOfferTaxYearTO;
import com.linkapital.core.services.indicative_offer.contract.to.IndicativeVolumeTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "All data of the learning offer three.")
@Getter
@Setter
@NoArgsConstructor
public class IndicativeOfferThreeTO extends IndicativeOfferTO {

    @ApiModelProperty(value = "The fiscal data of the indicative offer")
    private IndicativeOfferTaxTO indicativeOfferTax;

    @ApiModelProperty(value = "The annual tax data of the indicative offer")
    private IndicativeOfferTaxYearTO indicativeOfferTaxYear;

    @ApiModelProperty(value = "The offer volume data")
    private IndicativeVolumeTO indicativeVolume;

    public IndicativeOfferThreeTO withIndicativeOfferTax(IndicativeOfferTaxTO indicativeOfferTax) {
        setIndicativeOfferTax(indicativeOfferTax);
        return this;
    }

    public IndicativeOfferThreeTO withIndicativeOfferTaxYear(IndicativeOfferTaxYearTO indicativeOfferTaxYear) {
        setIndicativeOfferTaxYear(indicativeOfferTaxYear);
        return this;
    }

    public IndicativeOfferThreeTO withIndicativeVolume(IndicativeVolumeTO indicativeVolume) {
        setIndicativeVolume(indicativeVolume);
        return this;
    }

}
