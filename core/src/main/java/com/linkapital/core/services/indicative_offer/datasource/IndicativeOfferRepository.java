package com.linkapital.core.services.indicative_offer.datasource;

import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndicativeOfferRepository extends JpaRepository<IndicativeOffer, Long> {

}
