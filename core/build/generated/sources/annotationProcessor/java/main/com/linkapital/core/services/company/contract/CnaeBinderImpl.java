package com.linkapital.core.services.company.contract;

import com.linkapital.core.services.company.contract.to.CnaeTO;
import com.linkapital.core.services.company.datasource.domain.Cnae;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class CnaeBinderImpl implements CnaeBinder {

    @Override
    public CnaeTO bind(Cnae source) {
        if ( source == null ) {
            return null;
        }

        CnaeTO cnaeTO = new CnaeTO();

        if ( source.getId() != null ) {
            cnaeTO.setId( source.getId() );
        }
        cnaeTO.setCode( source.getCode() );
        cnaeTO.setDescription( source.getDescription() );
        cnaeTO.setBusinessActivity( source.getBusinessActivity() );
        cnaeTO.setSector( source.getSector() );

        return cnaeTO;
    }
}
