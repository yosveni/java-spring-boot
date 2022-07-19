package com.linkapital.core.services.commission.datasource;

import com.linkapital.core.services.commission.datasource.domain.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionRepository extends JpaRepository<Commission, Long> {

}
