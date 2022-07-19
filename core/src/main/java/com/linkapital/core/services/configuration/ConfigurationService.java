package com.linkapital.core.services.configuration;

import com.linkapital.core.exceptions.InvalidSystemConfigurationException;
import com.linkapital.core.exceptions.NotFoundSystemConfigurationException;
import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.configuration.datasource.domain.SysConfiguration;
import com.linkapital.core.util.generic.GenericFilterTO;

import java.util.List;

/**
 * Default interface for {@link ConfigurationService}
 * Has the responsibility to manage the system configuration
 */
public interface ConfigurationService {

    /**
     * Save a system configuration
     *
     * @param configuration {@link SysConfiguration} the system configuration to be register
     * @return {@link SysConfiguration} a created system configuration
     */
    SysConfiguration save(SysConfiguration configuration);

    /**
     * Get a system configuration by name
     *
     * @param name {@link String} the system configuration name
     * @return {@link SysConfiguration} a system configuration
     */
    SysConfiguration getByName(String name) throws NotFoundSystemConfigurationException;

    /**
     * Create a system configuration
     *
     * @param configuration {@link SysConfigurationTO} the system configuration to be created
     * @return {@link SysConfigurationTO} a created system configuration
     */
    SysConfigurationTO create(SysConfigurationTO configuration) throws InvalidSystemConfigurationException;

    /**
     * Update a system configuration
     *
     * @param configuration {@link SysConfigurationTO} the configuration to be deleted
     * @return {@link SysConfigurationTO} the updated configuration
     */
    SysConfigurationTO update(SysConfigurationTO configuration) throws NotFoundSystemConfigurationException, InvalidSystemConfigurationException;

    /**
     * Load a system configuration
     *
     * @param id {@link Long} an id of an available configuration
     * @return {@link SysConfigurationTO} an available system configuration
     */
    SysConfigurationTO load(long id) throws NotFoundSystemConfigurationException;

    /**
     * Delete a system configuration
     *
     * @param id {@link Long} the id of configuration to be deleted
     */
    void delete(long id) throws NotFoundSystemConfigurationException;

    /**
     * Load all the available systems configurations
     *
     * @return {@link List<SysConfigurationTO>} all the available system configurations
     */
    GenericFilterTO<SysConfigurationTO> loadAll();

    /**
     * Retrieve a configuration and transform into the given configuration class
     *
     * @param id        {@link Long} the configuration id to be retrieved
     * @param transform {@link Class} an object class to transform the retrieved configuration
     * @return {@link Object} a transformed object configuration
     */
    Object load(long id, Class transform);

}
