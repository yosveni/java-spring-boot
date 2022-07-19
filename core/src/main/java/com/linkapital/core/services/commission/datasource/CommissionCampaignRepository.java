package com.linkapital.core.services.commission.datasource;

import com.linkapital.core.services.commission.datasource.domain.CommissionCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommissionCampaignRepository extends JpaRepository<CommissionCampaign, Long> {


    boolean existsByIdAndBaseIsTrue(long id);

    Optional<CommissionCampaign> findByBaseIsTrue();

}
