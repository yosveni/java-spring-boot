package com.linkapital.core.services.whatsapp.contract;

import com.linkapital.core.services.whatsapp.contract.to.CreateUserWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UpdateUserWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UserWhatsAppTO;
import com.linkapital.core.services.whatsapp.datasource.domain.UserWhatsApp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class WhatsAppBinderImpl implements WhatsAppBinder {

    @Override
    public UserWhatsApp bind(CreateUserWhatsAppTO createUserWhatsAppTO) {
        if ( createUserWhatsAppTO == null ) {
            return null;
        }

        UserWhatsApp userWhatsApp = new UserWhatsApp();

        userWhatsApp.setName( createUserWhatsAppTO.getName() );
        userWhatsApp.setLastName( createUserWhatsAppTO.getLastName() );
        userWhatsApp.setNumber( createUserWhatsAppTO.getNumber() );
        userWhatsApp.setWorkPosition( createUserWhatsAppTO.getWorkPosition() );

        return userWhatsApp;
    }

    @Override
    public UserWhatsAppTO bind(UserWhatsApp userWhatsApp) {
        if ( userWhatsApp == null ) {
            return null;
        }

        UserWhatsAppTO userWhatsAppTO = new UserWhatsAppTO();

        if ( userWhatsApp.getId() != null ) {
            userWhatsAppTO.setId( userWhatsApp.getId() );
        }
        userWhatsAppTO.setName( userWhatsApp.getName() );
        userWhatsAppTO.setLastName( userWhatsApp.getLastName() );
        userWhatsAppTO.setNumber( userWhatsApp.getNumber() );
        userWhatsAppTO.setWorkPosition( userWhatsApp.getWorkPosition() );
        userWhatsAppTO.setTimesCalled( userWhatsApp.getTimesCalled() );
        userWhatsAppTO.setEnabled( userWhatsApp.isEnabled() );

        return userWhatsAppTO;
    }

    @Override
    public UserWhatsApp set(UserWhatsApp target, UpdateUserWhatsAppTO source) {
        if ( source == null ) {
            return null;
        }

        target.setName( source.getName() );
        target.setLastName( source.getLastName() );
        target.setNumber( source.getNumber() );
        target.setWorkPosition( source.getWorkPosition() );

        return target;
    }

    @Override
    public List<UserWhatsAppTO> bind(List<UserWhatsApp> source) {
        if ( source == null ) {
            return null;
        }

        List<UserWhatsAppTO> list = new ArrayList<UserWhatsAppTO>( source.size() );
        for ( UserWhatsApp userWhatsApp : source ) {
            list.add( bind( userWhatsApp ) );
        }

        return list;
    }
}
