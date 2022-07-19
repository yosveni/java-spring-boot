package com.linkapital.core.services.configuration.contract.validators;

import com.linkapital.core.exceptions.NotFoundSystemConfigurationException;
import com.linkapital.core.services.configuration.datasource.ConfigurationRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

@Aspect
@Component
public class DeleteConfigurationValidator {

    private final static String DEFAULT_ARGS_NAMES = "joinPoint, id";

    private final ConfigurationRepository configurationRepository;

    public DeleteConfigurationValidator(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Before(value = "execution(* com.linkapital.core.services.configuration.ConfigurationService+.delete(..)) && args(id) " +
            "|| execution(* com.linkapital.core.services.configuration.ConfigurationService+.load(..)) && args(id)",
            argNames = DEFAULT_ARGS_NAMES)
    public void beforeAdvice(JoinPoint joinPoint, long id) throws NotFoundSystemConfigurationException {
        var exists = configurationRepository.existsById(id);
        if (!exists)
            throw new NotFoundSystemConfigurationException(msg("system.configuration.not.exists"));
    }

}
