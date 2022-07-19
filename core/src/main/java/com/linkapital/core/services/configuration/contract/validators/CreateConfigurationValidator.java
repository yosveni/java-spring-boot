package com.linkapital.core.services.configuration.contract.validators;

import com.linkapital.core.exceptions.InvalidSystemConfigurationException;
import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.configuration.datasource.ConfigurationRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

@Aspect
@Component
public class CreateConfigurationValidator {

    private static final String DEFAULT_ARGS_NAMES = "joinPoint, configuration";
    private final ConfigurationRepository configurationRepository;

    public CreateConfigurationValidator(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Before(value = "execution(* com.linkapital.core.services.configuration.ConfigurationService+.create(..)) &&" +
            " args(configuration)", argNames = DEFAULT_ARGS_NAMES)
    public void beforeAdvice(JoinPoint joinPoint, SysConfigurationTO configuration)
            throws InvalidSystemConfigurationException {
        var exists = configurationRepository.existsByName(configuration.getName());
        if (exists)
            throw new InvalidSystemConfigurationException(msg("system.configuration.exists"));
    }

}
