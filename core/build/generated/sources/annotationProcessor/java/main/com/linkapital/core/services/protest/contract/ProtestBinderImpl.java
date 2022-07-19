package com.linkapital.core.services.protest.contract;

import com.linkapital.core.services.protest.contract.to.ProtestInformationTO;
import com.linkapital.core.services.protest.contract.to.ProtestLightTO;
import com.linkapital.core.services.protest.contract.to.ProtestRegistryTO;
import com.linkapital.core.services.protest.contract.to.ProtestTO;
import com.linkapital.core.services.protest.datasource.domain.Protest;
import com.linkapital.core.services.protest.datasource.domain.ProtestInformation;
import com.linkapital.core.services.protest.datasource.domain.ProtestRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class ProtestBinderImpl implements ProtestBinder {

    @Override
    public ProtestLightTO bind(Protest source) {
        if ( source == null ) {
            return null;
        }

        ProtestLightTO protestLightTO = new ProtestLightTO();

        protestLightTO.setAssignorName( source.getAssignorName() );
        protestLightTO.setValue( source.getValue() );
        protestLightTO.setConsultDate( source.getConsultDate() );

        return protestLightTO;
    }

    @Override
    public ProtestInformationTO bind(ProtestInformation source) {
        if ( source == null ) {
            return null;
        }

        ProtestInformationTO protestInformationTO = new ProtestInformationTO();

        if ( source.getId() != null ) {
            protestInformationTO.setId( source.getId() );
        }
        protestInformationTO.setDocument( source.getDocument() );
        protestInformationTO.setTotal( source.getTotal() );
        protestInformationTO.setTotalError( source.getTotalError() );
        protestInformationTO.setCreated( source.getCreated() );
        protestInformationTO.setProtestRegistries( protestRegistrySetToProtestRegistryTOList( source.getProtestRegistries() ) );

        protestInformationTO.setAnalysis( source == null ? null : getAnalysis(source.getAnalysis()) );

        return protestInformationTO;
    }

    protected ProtestTO protestToProtestTO(Protest protest) {
        if ( protest == null ) {
            return null;
        }

        ProtestTO protestTO = new ProtestTO();

        if ( protest.getId() != null ) {
            protestTO.setId( protest.getId() );
        }
        protestTO.setKey( protest.getKey() );
        protestTO.setPresenterName( protest.getPresenterName() );
        protestTO.setAssignorName( protest.getAssignorName() );
        protestTO.setValue( protest.getValue() );
        protestTO.setHasConsent( protest.isHasConsent() );
        protestTO.setConsultDate( protest.getConsultDate() );
        protestTO.setDueDate( protest.getDueDate() );
        protestTO.setArea( protest.getArea() );

        return protestTO;
    }

    protected List<ProtestTO> protestSetToProtestTOList(Set<Protest> set) {
        if ( set == null ) {
            return null;
        }

        List<ProtestTO> list = new ArrayList<ProtestTO>( set.size() );
        for ( Protest protest : set ) {
            list.add( protestToProtestTO( protest ) );
        }

        return list;
    }

    protected ProtestRegistryTO protestRegistryToProtestRegistryTO(ProtestRegistry protestRegistry) {
        if ( protestRegistry == null ) {
            return null;
        }

        ProtestRegistryTO protestRegistryTO = new ProtestRegistryTO();

        if ( protestRegistry.getId() != null ) {
            protestRegistryTO.setId( protestRegistry.getId() );
        }
        protestRegistryTO.setName( protestRegistry.getName() );
        protestRegistryTO.setPhone( protestRegistry.getPhone() );
        protestRegistryTO.setAddress( protestRegistry.getAddress() );
        protestRegistryTO.setCityCode( protestRegistry.getCityCode() );
        protestRegistryTO.setCityCodeIbge( protestRegistry.getCityCodeIbge() );
        protestRegistryTO.setMunicipality( protestRegistry.getMunicipality() );
        protestRegistryTO.setCity( protestRegistry.getCity() );
        protestRegistryTO.setNeighborhood( protestRegistry.getNeighborhood() );
        protestRegistryTO.setUf( protestRegistry.getUf() );
        protestRegistryTO.setCode( protestRegistry.getCode() );
        protestRegistryTO.setAmount( protestRegistry.getAmount() );
        protestRegistryTO.setSearchPeriod( protestRegistry.getSearchPeriod() );
        protestRegistryTO.setUpdateDate( protestRegistry.getUpdateDate() );
        protestRegistryTO.setProtests( protestSetToProtestTOList( protestRegistry.getProtests() ) );

        return protestRegistryTO;
    }

    protected List<ProtestRegistryTO> protestRegistrySetToProtestRegistryTOList(Set<ProtestRegistry> set) {
        if ( set == null ) {
            return null;
        }

        List<ProtestRegistryTO> list = new ArrayList<ProtestRegistryTO>( set.size() );
        for ( ProtestRegistry protestRegistry : set ) {
            list.add( protestRegistryToProtestRegistryTO( protestRegistry ) );
        }

        return list;
    }
}
