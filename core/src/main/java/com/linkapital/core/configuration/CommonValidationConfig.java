package com.linkapital.core.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.linkapital.core.cadastral_score.contract.validators"})
public class CommonValidationConfig {

}
