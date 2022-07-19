package com.linkapital.core.services.security.datasource;

import com.linkapital.core.services.security.datasource.domain.UserTemp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTempRepository extends JpaRepository<UserTemp, Long> {

    Optional<UserTemp> findByEmail(String email);

}
