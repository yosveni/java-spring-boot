package com.linkapital.core.services.security.contract;

import com.linkapital.core.services.security.contract.to.UserTempTO;
import com.linkapital.core.services.security.datasource.domain.UserTemp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class UserTempBinderImpl implements UserTempBinder {

    @Override
    public UserTempTO bind(UserTemp source) {
        if ( source == null ) {
            return null;
        }

        UserTempTO userTempTO = new UserTempTO();

        userTempTO.setId( source.getId() );
        userTempTO.setEmail( source.getEmail() );
        userTempTO.setName( source.getName() );
        userTempTO.setCodeCountryPhone( source.getCodeCountryPhone() );
        userTempTO.setPhone( source.getPhone() );
        userTempTO.setLinkedingContact( source.getLinkedingContact() );
        userTempTO.setCpf( source.getCpf() );
        userTempTO.setPartner( source.isPartner() );
        userTempTO.setCreated( source.getCreated() );
        userTempTO.setModified( source.getModified() );

        return userTempTO;
    }

    @Override
    public List<UserTempTO> bind(List<UserTemp> source) {
        if ( source == null ) {
            return null;
        }

        List<UserTempTO> list = new ArrayList<UserTempTO>( source.size() );
        for ( UserTemp userTemp : source ) {
            list.add( bind( userTemp ) );
        }

        return list;
    }
}
