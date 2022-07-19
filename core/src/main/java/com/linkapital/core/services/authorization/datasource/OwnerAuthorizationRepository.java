package com.linkapital.core.services.authorization.datasource;

import com.linkapital.core.services.authorization.datasource.domain.OwnerAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerAuthorizationRepository extends JpaRepository<OwnerAuthorization, Long> {

    Optional<OwnerAuthorization> findByToken(String token);

}
