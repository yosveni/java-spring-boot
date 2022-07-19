package com.linkapital.core.services.configuration.contract;

import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.configuration.datasource.domain.SysConfiguration;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:17-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class SysConfigurationBinderImpl implements SysConfigurationBinder {

    @Override
    public SysConfiguration bind(SysConfigurationTO source) {
        if ( source == null ) {
            return null;
        }

        SysConfiguration sysConfiguration = new SysConfiguration();

        sysConfiguration.setConfiguration( source.getConfiguration() );
        sysConfiguration.setId( source.getId() );
        sysConfiguration.setName( source.getName() );
        sysConfiguration.setDescription( source.getDescription() );

        return sysConfiguration;
    }

    @Override
    public SysConfigurationTO bind(SysConfiguration source) {
        if ( source == null ) {
            return null;
        }

        SysConfigurationTO sysConfigurationTO = new SysConfigurationTO();

        sysConfigurationTO.setConfiguration( source.getConfiguration() );
        sysConfigurationTO.setId( source.getId() );
        sysConfigurationTO.setName( source.getName() );
        sysConfigurationTO.setDescription( source.getDescription() );

        return sysConfigurationTO;
    }

    @Override
    public SysConfiguration set(SysConfiguration target, SysConfigurationTO source) {
        if ( source == null ) {
            return null;
        }

        target.setConfiguration( source.getConfiguration() );
        target.setName( source.getName() );
        target.setDescription( source.getDescription() );

        return target;
    }
}
