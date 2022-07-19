package com.linkapital.core.services.commission.datasource;

import com.linkapital.core.services.commission.datasource.domain.CommissionCampaignAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionCampaignAttributeRepository extends JpaRepository<CommissionCampaignAttribute, Long> {

}
