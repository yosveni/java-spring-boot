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
@DiscriminatorValue("3")
public class IndicativeOfferThree extends IndicativeOffer {

    @Column(name = "min_offer_tax")
    private double minOfferTax;

    @Column(name = "max_offer_tax")
    private double maxOfferTax;

    @Column(name = "min_offer_tax_year")
    private double minOfferTaxYear;

    @Column(name = "max_offer_tax_year")
    private double maxOfferTaxYear;

    @Column(name = "volume_min")
    private double volumeMin;

    @Column(name = "volume_max")
    private double volumeMax;

    public IndicativeOfferThree withMinOfferTax(double minOfferTax) {
        setMinOfferTax(minOfferTax);
        return this;
    }

    public IndicativeOfferThree withMaxOfferTax(double maxOfferTax) {
        setMaxOfferTax(maxOfferTax);
        return this;
    }

    public IndicativeOfferThree withMinOfferTaxYear(double minOfferTaxYear) {
        setMinOfferTaxYear(minOfferTaxYear);
        return this;
    }

    public IndicativeOfferThree withMaxOfferTaxYear(double maxOfferTaxYear) {
        setMaxOfferTaxYear(maxOfferTaxYear);
        return this;
    }

    public IndicativeOfferThree withVolumeMin(double volumeMin) {
        setVolumeMin(volumeMin);
        return this;
    }

    public IndicativeOfferThree withVolumeMax(double volumeMax) {
        setVolumeMax(volumeMax);
        return this;
    }

}

