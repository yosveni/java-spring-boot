package com.linkapital.core.services.offer.contract.to.create;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;

@Getter
@Setter
@DiscriminatorValue("3")
public class CreateOfferThreeTO extends CreateOfferTO {

}
