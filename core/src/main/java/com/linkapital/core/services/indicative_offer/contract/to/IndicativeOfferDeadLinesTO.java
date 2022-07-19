package com.linkapital.core.services.indicative_offer.contract.to;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndicativeOfferDeadLinesTO implements Serializable {

    @ApiModelProperty(value = "The min value for the offer dead lines")
    private double minOfferDeadLine;

    @ApiModelProperty(value = "The min value for the offer dead lines")
    private double maxOfferDeadLine;

    public IndicativeOfferDeadLinesTO withMinOfferDeadLine(double minOfferDeadLine) {
        setMinOfferDeadLine(minOfferDeadLine);
        return this;
    }

    public IndicativeOfferDeadLinesTO withMaxOfferDeadLine(double maxOfferDeadLine) {
        setMaxOfferDeadLine(maxOfferDeadLine);
        return this;
    }

}
