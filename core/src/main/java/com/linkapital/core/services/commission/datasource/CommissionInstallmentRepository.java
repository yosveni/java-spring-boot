package com.linkapital.core.services.commission.datasource;

import com.linkapital.core.services.commission.datasource.domain.CommissionInstallment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommissionInstallmentRepository extends JpaRepository<CommissionInstallment, Long> {

    List<CommissionInstallment> findAllByIdIn(List<Long> ids);

}
