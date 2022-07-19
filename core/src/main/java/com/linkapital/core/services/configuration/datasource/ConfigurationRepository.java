package com.linkapital.core.services.configuration.datasource;

import com.linkapital.core.services.configuration.datasource.domain.SysConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigurationRepository extends JpaRepository<SysConfiguration, Long> {

    boolean existsByName(String name);

    Optional<SysConfiguration> findByName(String name);

}
