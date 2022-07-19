package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.shared.contract.to.AddressTO;
import com.linkapital.core.services.shared.contract.to.CreateAddressTO;
import com.linkapital.core.services.shared.datasource.domain.Address;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:17-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class AddressBinderImpl implements AddressBinder {

    @Override
    public AddressTO bind(Address source) {
        if ( source == null ) {
            return null;
        }

        AddressTO addressTO = new AddressTO();

        if ( source.getId() != null ) {
            addressTO.setId( source.getId() );
        }
        addressTO.setNeighborhood( source.getNeighborhood() );
        addressTO.setOriginalNeighborhood( source.getOriginalNeighborhood() );
        addressTO.setZip( source.getZip() );
        addressTO.setAddress1( source.getAddress1() );
        addressTO.setAddress2( source.getAddress2() );
        addressTO.setMRegion( source.getMRegion() );
        addressTO.setMicroRegion( source.getMicroRegion() );
        addressTO.setRegion( source.getRegion() );
        addressTO.setCountry( source.getCountry() );
        addressTO.setCodeCountry( source.getCodeCountry() );
        addressTO.setMunicipality( source.getMunicipality() );
        addressTO.setCodeMunicipality( source.getCodeMunicipality() );
        addressTO.setBorderMunicipality( source.getBorderMunicipality() );
        addressTO.setNumber( source.getNumber() );
        addressTO.setPrecision( source.getPrecision() );
        addressTO.setUf( source.getUf() );
        addressTO.setRegistryUf( source.getRegistryUf() );
        addressTO.setBuildingType( source.getBuildingType() );
        addressTO.setFormattedAddress( source.getFormattedAddress() );
        addressTO.setLatitude( source.getLatitude() );
        addressTO.setLongitude( source.getLongitude() );
        addressTO.setDeliveryRestriction( source.isDeliveryRestriction() );
        addressTO.setResidentialAddress( source.isResidentialAddress() );
        addressTO.setLatestAddress( source.isLatestAddress() );
        addressTO.setCollectiveBuilding( source.isCollectiveBuilding() );
        List<String> list = source.getRfPhones();
        if ( list != null ) {
            addressTO.setRfPhones( new ArrayList<String>( list ) );
        }

        return addressTO;
    }

    @Override
    public Address bind(CreateAddressTO source) {
        if ( source == null ) {
            return null;
        }

        Address address = new Address();

        address.setNeighborhood( source.getNeighborhood() );
        address.setZip( source.getZip() );
        address.setAddress1( source.getAddress1() );
        address.setAddress2( source.getAddress2() );
        address.setMunicipality( source.getMunicipality() );
        address.setNumber( source.getNumber() );
        address.setUf( source.getUf() );

        return address;
    }
}
