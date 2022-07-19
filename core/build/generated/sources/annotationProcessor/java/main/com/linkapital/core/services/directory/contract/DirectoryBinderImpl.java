package com.linkapital.core.services.directory.contract;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:17-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class DirectoryBinderImpl implements DirectoryBinder {

    @Override
    public DirectoryTO bind(Directory source) {
        if ( source == null ) {
            return null;
        }

        DirectoryTO directoryTO = new DirectoryTO();

        directoryTO.setId( source.getId() );
        directoryTO.setName( source.getName() );
        directoryTO.setExt( source.getExt() );
        directoryTO.setUrl( source.getUrl() );
        directoryTO.setType( source.getType() );

        return directoryTO;
    }

    @Override
    public List<DirectoryTO> bind(Collection<Directory> source) {
        if ( source == null ) {
            return null;
        }

        List<DirectoryTO> list = new ArrayList<DirectoryTO>( source.size() );
        for ( Directory directory : source ) {
            list.add( bind( directory ) );
        }

        return list;
    }
}
