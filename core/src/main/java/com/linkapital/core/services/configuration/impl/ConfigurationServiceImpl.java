package com.linkapital.core.services.configuration.impl;

import com.linkapital.core.exceptions.InvalidSystemConfigurationException;
import com.linkapital.core.exceptions.NotFoundSystemConfigurationException;
import com.linkapital.core.services.configuration.ConfigurationService;
import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.configuration.datasource.ConfigurationRepository;
import com.linkapital.core.services.configuration.datasource.domain.SysConfiguration;
import com.linkapital.core.util.generic.GenericFilterTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.configuration.contract.SysConfigurationBinder.CONFIGURATION_BINDER;

@Service("configurationServices")
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public SysConfiguration save(SysConfiguration configuration) {
        return configurationRepository.save(configuration);
    }

    @Override
    public SysConfiguration getByName(String name) throws NotFoundSystemConfigurationException {
        return configurationRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundSystemConfigurationException(msg("system.configuration.not.exists")));
    }

    @Override
    public SysConfigurationTO create(SysConfigurationTO to) throws InvalidSystemConfigurationException {
        return Optional
                .of(configurationRepository.save(CONFIGURATION_BINDER.bind(to)))
                .map(CONFIGURATION_BINDER::bind)
                .orElseThrow(() -> new InvalidSystemConfigurationException(msg("system.configuration.error.save")));
    }

    @Override
    public SysConfigurationTO update(SysConfigurationTO to) throws NotFoundSystemConfigurationException, InvalidSystemConfigurationException {
        return Optional
                .of(getById(to.getId()))
                .flatMap(target -> Optional
                        .of(configurationRepository.save(CONFIGURATION_BINDER.set(target, to))))
                .map(CONFIGURATION_BINDER::bind)
                .orElseThrow(() -> new InvalidSystemConfigurationException(msg("system.configuration.error.save")));
    }

    @Override
    public SysConfigurationTO load(long id) throws NotFoundSystemConfigurationException {
        return Optional
                .of(getById(id))
                .map(CONFIGURATION_BINDER::bind)
                .orElse(null);
    }

    @Override
    public void delete(long id) throws NotFoundSystemConfigurationException {
        Optional
                .of(getById(id))
                .ifPresent(configurationRepository::delete);
    }

    @Override
    public GenericFilterTO<SysConfigurationTO> loadAll() {
        return Optional
                .of(Optional.of(configurationRepository.findAll())
                        .get()
                        .stream()
                        .map(CONFIGURATION_BINDER::bind)
                        .toList())
                .map(CONFIGURATION_BINDER::bind)
                .orElse(new GenericFilterTO<>());
    }

    @Override
    public Object load(long id, Class transform) {
        return Optional
                .of(configurationRepository.findById(id))
                .get()
                .map(configuration -> CONFIGURATION_BINDER.bindTransform(configuration, transform))
                .orElse(new Object());
    }

    private SysConfiguration getById(long id) throws NotFoundSystemConfigurationException {
        return configurationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundSystemConfigurationException(msg("system.configuration.not.exists")));
    }

}
