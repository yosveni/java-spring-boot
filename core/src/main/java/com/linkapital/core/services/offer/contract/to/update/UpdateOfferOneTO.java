package com.linkapital.core.services.offer.contract.to.update;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;

@Getter
@Setter
@DiscriminatorValue("1")
public class UpdateOfferOneTO extends UpdateOfferTO {

}
