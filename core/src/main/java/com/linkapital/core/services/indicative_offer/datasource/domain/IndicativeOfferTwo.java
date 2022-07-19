package com.linkapital.core.services.indicative_offer.datasource.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("2")
public class IndicativeOfferTwo extends IndicativeOffer {

    @Column(name = "min_offer_tax")
    private double minOfferTax;

    @Column(name = "max_offer_tax")
    private double maxOfferTax;

    @Column(name = "min_dead_line")
    private double minOfferDeadLine;

    @Column(name = "max_dead_line")
    private double maxOfferDeadLine;

    public IndicativeOfferTwo withMinOfferTax(double minOfferTax) {
        setMinOfferTax(minOfferTax);
        return this;
    }

    public IndicativeOfferTwo withMaxOfferTax(double maxOfferTax) {
        setMaxOfferTax(maxOfferTax);
        return this;
    }

    public IndicativeOfferTwo withMinOfferDeadLine(double minOfferDeadLine) {
        setMinOfferDeadLine(minOfferDeadLine);
        return this;
    }

    public IndicativeOfferTwo withMaxOfferDeadLine(double maxOfferDeadLine) {
        setMaxOfferDeadLine(maxOfferDeadLine);
        return this;
    }

}

