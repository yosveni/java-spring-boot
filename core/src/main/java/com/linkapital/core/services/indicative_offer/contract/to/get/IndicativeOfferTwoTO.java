package com.linkapital.core.services.indicative_offer.contract.to.get;

import com.linkapital.core.services.indicative_offer.contract.to.IndicativeOfferDeadLinesTO;
import com.linkapital.core.services.indicative_offer.contract.to.IndicativeOfferTaxTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "All data of the learning offer two.")
@Getter
@Setter
@NoArgsConstructor
public class IndicativeOfferTwoTO extends IndicativeOfferTO {

    @ApiModelProperty(value = "Tax values of the offer")
    private IndicativeOfferTaxTO indicativeOfferTax;

    @ApiModelProperty(value = "The indicative vales for the offer dead lines.")
    private IndicativeOfferDeadLinesTO offerDeadLines;

    public IndicativeOfferTwoTO withIndicativeOfferTax(IndicativeOfferTaxTO indicativeOfferTax) {
        setIndicativeOfferTax(indicativeOfferTax);
        return this;
    }

    public IndicativeOfferTwoTO withOfferDeadLines(IndicativeOfferDeadLinesTO offerDeadLines) {
        setOfferDeadLines(offerDeadLines);
        return this;
    }

}
