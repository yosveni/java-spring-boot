package com.linkapital.core.services.identification.contract;

import com.linkapital.core.services.identification.contract.to.IdentificationTO;
import com.linkapital.core.services.identification.datasource.domain.Identification;
import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class IdentificationBinderImpl implements IdentificationBinder {

    @Override
    public IdentificationTO bind(Identification source) {
        if ( source == null ) {
            return null;
        }

        IdentificationTO identificationTO = new IdentificationTO();

        if ( source.getId() != null ) {
            identificationTO.setId( source.getId() );
        }
        identificationTO.setType( source.getType() );
        identificationTO.setValid( source.getValid() );
        identificationTO.setCreated( source.getCreated() );
        identificationTO.setState( source.getState() );
        byte[] document = source.getDocument();
        if ( document != null ) {
            identificationTO.setDocument( Arrays.copyOf( document, document.length ) );
        }
        byte[] reverseDocument = source.getReverseDocument();
        if ( reverseDocument != null ) {
            identificationTO.setReverseDocument( Arrays.copyOf( reverseDocument, reverseDocument.length ) );
        }
        byte[] selfieCapture = source.getSelfieCapture();
        if ( selfieCapture != null ) {
            identificationTO.setSelfieCapture( Arrays.copyOf( selfieCapture, selfieCapture.length ) );
        }

        return identificationTO;
    }
}
