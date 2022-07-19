package com.linkapital.core.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.linkapital.core.services.*.datasource.domain")
@EnableJpaRepositories("com.linkapital.core.services.*.datasource")
public class DataSourceConfig {

}
