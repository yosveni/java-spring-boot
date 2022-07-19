package com.linkapital.core.services.indicative_offer.datasource.domain;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Entity
@DiscriminatorValue("4")
public class IndicativeOfferFour extends IndicativeOffer {

}

