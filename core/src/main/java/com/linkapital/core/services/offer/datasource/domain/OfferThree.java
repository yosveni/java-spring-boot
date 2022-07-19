package com.linkapital.core.services.offer.datasource.domain;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Entity
@DiscriminatorValue("3")
public class OfferThree extends Offer {

}
