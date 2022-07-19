package com.linkapital.core.services.offer.datasource;

import com.linkapital.core.services.offer.datasource.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByCnpj(String cnpj);

    List<Offer> findAllByIdIn(List<Long> ids);

    List<Offer> findAllByUserIdAndCommissionIsNotNull(long userId);

}
