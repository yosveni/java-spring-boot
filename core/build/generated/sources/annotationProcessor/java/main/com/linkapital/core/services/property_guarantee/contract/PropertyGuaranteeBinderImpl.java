package com.linkapital.core.services.property_guarantee.contract;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.property_guarantee.contract.to.CreatePropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.contract.to.PropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.contract.to.UpdatePropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.datasource.domain.PropertyGuarantee;
import com.linkapital.core.services.shared.contract.to.AddressTO;
import com.linkapital.core.services.shared.contract.to.CreateAddressTO;
import com.linkapital.core.services.shared.contract.to.UpdateAddressTO;
import com.linkapital.core.services.shared.datasource.domain.Address;
import com.linkapital.core.services.shared.datasource.domain.Property;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:17-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class PropertyGuaranteeBinderImpl implements PropertyGuaranteeBinder {

    @Override
    public PropertyGuaranteeTO bind(Property source) {
        if ( source == null ) {
            return null;
        }

        PropertyGuaranteeTO propertyGuaranteeTO = new PropertyGuaranteeTO();

        propertyGuaranteeTO.setRegistryNumber( source.getRegistryNumber() );
        propertyGuaranteeTO.setEvaluationValue( source.getEvaluationValue() );
        propertyGuaranteeTO.setBuiltArea( source.getBuiltArea() );
        propertyGuaranteeTO.setGroundArea( source.getGroundArea() );
        if ( source.getId() != null ) {
            propertyGuaranteeTO.setId( source.getId() );
        }
        propertyGuaranteeTO.setAddress( addressToAddressTO( source.getAddress() ) );

        return propertyGuaranteeTO;
    }

    @Override
    public PropertyGuaranteeTO bind(PropertyGuarantee source) {
        if ( source == null ) {
            return null;
        }

        PropertyGuaranteeTO propertyGuaranteeTO = new PropertyGuaranteeTO();

        propertyGuaranteeTO.setRegistryNumber( source.getRegistryNumber() );
        propertyGuaranteeTO.setReferenceProperty( source.getReferenceProperty() );
        propertyGuaranteeTO.setEvaluationValue( source.getEvaluationValue() );
        propertyGuaranteeTO.setBuiltArea( source.getBuiltArea() );
        propertyGuaranteeTO.setGroundArea( source.getGroundArea() );
        propertyGuaranteeTO.setType( source.getType() );
        propertyGuaranteeTO.setOwnerType( source.getOwnerType() );
        if ( source.getId() != null ) {
            propertyGuaranteeTO.setId( source.getId() );
        }
        propertyGuaranteeTO.setAddress( addressToAddressTO( source.getAddress() ) );
        propertyGuaranteeTO.setDocument( directoryToDirectoryTO( source.getDocument() ) );

        return propertyGuaranteeTO;
    }

    @Override
    public PropertyGuarantee bind(CreatePropertyGuaranteeTO source) {
        if ( source == null ) {
            return null;
        }

        PropertyGuarantee propertyGuarantee = new PropertyGuarantee();

        propertyGuarantee.setRegistryNumber( source.getRegistryNumber() );
        propertyGuarantee.setReferenceProperty( source.getReferenceProperty() );
        propertyGuarantee.setEvaluationValue( source.getEvaluationValue() );
        propertyGuarantee.setBuiltArea( source.getBuiltArea() );
        propertyGuarantee.setGroundArea( source.getGroundArea() );
        propertyGuarantee.setType( source.getType() );
        propertyGuarantee.setOwnerType( source.getOwnerType() );
        propertyGuarantee.setAddress( createAddressTOToAddress( source.getAddress() ) );

        return propertyGuarantee;
    }

    @Override
    public PropertyGuarantee set(UpdatePropertyGuaranteeTO source, PropertyGuarantee target) {
        if ( source == null ) {
            return null;
        }

        target.setRegistryNumber( source.getRegistryNumber() );
        target.setReferenceProperty( source.getReferenceProperty() );
        target.setEvaluationValue( source.getEvaluationValue() );
        target.setBuiltArea( source.getBuiltArea() );
        target.setGroundArea( source.getGroundArea() );
        target.setType( source.getType() );
        target.setOwnerType( source.getOwnerType() );
        if ( source.getAddress() != null ) {
            if ( target.getAddress() == null ) {
                target.setAddress( new Address() );
            }
            updateAddressTOToAddress( source.getAddress(), target.getAddress() );
        }
        else {
            target.setAddress( null );
        }

        return target;
    }

    @Override
    public List<PropertyGuaranteeTO> bindToPropertyGuaranteeTO(Collection<PropertyGuarantee> source) {
        if ( source == null ) {
            return null;
        }

        List<PropertyGuaranteeTO> list = new ArrayList<PropertyGuaranteeTO>( source.size() );
        for ( PropertyGuarantee propertyGuarantee : source ) {
            list.add( bind( propertyGuarantee ) );
        }

        return list;
    }

    protected AddressTO addressToAddressTO(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressTO addressTO = new AddressTO();

        if ( address.getId() != null ) {
            addressTO.setId( address.getId() );
        }
        addressTO.setNeighborhood( address.getNeighborhood() );
        addressTO.setOriginalNeighborhood( address.getOriginalNeighborhood() );
        addressTO.setZip( address.getZip() );
        addressTO.setAddress1( address.getAddress1() );
        addressTO.setAddress2( address.getAddress2() );
        addressTO.setMRegion( address.getMRegion() );
        addressTO.setMicroRegion( address.getMicroRegion() );
        addressTO.setRegion( address.getRegion() );
        addressTO.setCountry( address.getCountry() );
        addressTO.setCodeCountry( address.getCodeCountry() );
        addressTO.setMunicipality( address.getMunicipality() );
        addressTO.setCodeMunicipality( address.getCodeMunicipality() );
        addressTO.setBorderMunicipality( address.getBorderMunicipality() );
        addressTO.setNumber( address.getNumber() );
        addressTO.setPrecision( address.getPrecision() );
        addressTO.setUf( address.getUf() );
        addressTO.setRegistryUf( address.getRegistryUf() );
        addressTO.setBuildingType( address.getBuildingType() );
        addressTO.setFormattedAddress( address.getFormattedAddress() );
        addressTO.setLatitude( address.getLatitude() );
        addressTO.setLongitude( address.getLongitude() );
        addressTO.setDeliveryRestriction( address.isDeliveryRestriction() );
        addressTO.setResidentialAddress( address.isResidentialAddress() );
        addressTO.setLatestAddress( address.isLatestAddress() );
        addressTO.setCollectiveBuilding( address.isCollectiveBuilding() );
        List<String> list = address.getRfPhones();
        if ( list != null ) {
            addressTO.setRfPhones( new ArrayList<String>( list ) );
        }

        return addressTO;
    }

    protected DirectoryTO directoryToDirectoryTO(Directory directory) {
        if ( directory == null ) {
            return null;
        }

        DirectoryTO directoryTO = new DirectoryTO();

        directoryTO.setId( directory.getId() );
        directoryTO.setName( directory.getName() );
        directoryTO.setExt( directory.getExt() );
        directoryTO.setUrl( directory.getUrl() );
        directoryTO.setType( directory.getType() );

        return directoryTO;
    }

    protected Address createAddressTOToAddress(CreateAddressTO createAddressTO) {
        if ( createAddressTO == null ) {
            return null;
        }

        Address address = new Address();

        address.setNeighborhood( createAddressTO.getNeighborhood() );
        address.setZip( createAddressTO.getZip() );
        address.setAddress1( createAddressTO.getAddress1() );
        address.setAddress2( createAddressTO.getAddress2() );
        address.setMunicipality( createAddressTO.getMunicipality() );
        address.setNumber( createAddressTO.getNumber() );
        address.setUf( createAddressTO.getUf() );

        return address;
    }

    protected void updateAddressTOToAddress(UpdateAddressTO updateAddressTO, Address mappingTarget) {
        if ( updateAddressTO == null ) {
            return;
        }

        mappingTarget.setId( updateAddressTO.getId() );
        mappingTarget.setNeighborhood( updateAddressTO.getNeighborhood() );
        mappingTarget.setZip( updateAddressTO.getZip() );
        mappingTarget.setAddress1( updateAddressTO.getAddress1() );
        mappingTarget.setAddress2( updateAddressTO.getAddress2() );
        mappingTarget.setMunicipality( updateAddressTO.getMunicipality() );
        mappingTarget.setNumber( updateAddressTO.getNumber() );
        mappingTarget.setUf( updateAddressTO.getUf() );
    }
}
