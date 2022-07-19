package com.linkapital.core.services.reputation.datasource;

import com.linkapital.core.services.reputation.datasource.domain.Reputation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReputationRepository extends JpaRepository<Reputation, Long> {

}
