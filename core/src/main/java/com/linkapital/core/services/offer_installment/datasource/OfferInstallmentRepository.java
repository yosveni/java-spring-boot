package com.linkapital.core.services.offer_installment.datasource;

import com.linkapital.core.services.offer_installment.datasource.domain.OfferInstallment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferInstallmentRepository extends JpaRepository<OfferInstallment, Long> {

}
