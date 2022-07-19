package com.linkapital.core.services.integrations.contract;

import com.google.maps.model.GeocodingResult;
import com.linkapital.core.services.integrations.contract.to.GoogleLocationTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class GoogleMapsBinderImpl implements GoogleMapsBinder {

    @Override
    public GoogleLocationTO bindGoogleLocationTO(GeocodingResult source) {
        if ( source == null ) {
            return null;
        }

        GoogleLocationTO googleLocationTO = new GoogleLocationTO();

        googleLocationTO.setFormattedAddress( source.formattedAddress );

        googleLocationTO.setLatitude( source.geometry.location.lat );
        googleLocationTO.setLongitude( source.geometry.location.lng );

        return googleLocationTO;
    }
}
