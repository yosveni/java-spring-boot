package com.linkapital.core.services.indicative_offer.contract;

import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferOneTO;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferOne;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T22:29:28-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class IndicativeOfferBinderImpl implements IndicativeOfferBinder {

    @Override
    public IndicativeOfferOneTO bindOfferOneTO(IndicativeOfferOne source) {
        if ( source == null ) {
            return null;
        }

        IndicativeOfferOneTO indicativeOfferOneTO = new IndicativeOfferOneTO();

        indicativeOfferOneTO.setId( source.getId() );
        indicativeOfferOneTO.setDeadline( source.getDeadline() );
        indicativeOfferOneTO.setType( source.getType() );
        indicativeOfferOneTO.setVolume( source.getVolume() );
        indicativeOfferOneTO.setTax( source.getTax() );
        indicativeOfferOneTO.setPrecision( source.getPrecision() );
        indicativeOfferOneTO.setState( source.getState() );

        return indicativeOfferOneTO;
    }
}
