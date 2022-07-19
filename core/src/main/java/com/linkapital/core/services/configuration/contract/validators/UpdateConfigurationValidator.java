package com.linkapital.core.services.configuration.contract.validators;

import com.linkapital.core.exceptions.NotFoundSystemConfigurationException;
import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.configuration.datasource.ConfigurationRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

@Aspect
@Component
public class UpdateConfigurationValidator {

    private final static String DEFAULT_ARGS_NAMES = "joinPoint, configuration";

    private final ConfigurationRepository configurationRepository;

    public UpdateConfigurationValidator(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Before(value = "execution(* com.linkapital.core.services.configuration.ConfigurationService+.update(..)) && args(configuration)",
            argNames = DEFAULT_ARGS_NAMES)
    public void beforeAdvice(JoinPoint joinPoint, SysConfigurationTO configuration) throws NotFoundSystemConfigurationException {
        boolean exists = configurationRepository.existsById(configuration.getId());
        if (!exists) throw new NotFoundSystemConfigurationException(msg("system.configuration.not.exists"));
    }

}
