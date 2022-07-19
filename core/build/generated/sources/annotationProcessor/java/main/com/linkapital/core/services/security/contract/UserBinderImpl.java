package com.linkapital.core.services.security.contract;

import com.linkapital.core.services.security.contract.to.LightUserTO;
import com.linkapital.core.services.security.contract.to.RegisterUserTO;
import com.linkapital.core.services.security.contract.to.RoleTO;
import com.linkapital.core.services.security.contract.to.UserActiveTO;
import com.linkapital.core.services.security.contract.to.UserAuthenticatedTO;
import com.linkapital.core.services.security.contract.to.UserGuideTO;
import com.linkapital.core.services.security.contract.to.UserIdentificationTO;
import com.linkapital.core.services.security.contract.to.UserTO;
import com.linkapital.core.services.security.contract.to.create.CreateUserTO;
import com.linkapital.core.services.security.contract.to.update.UpdateUserTO;
import com.linkapital.core.services.security.datasource.domain.Role;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.services.security.datasource.domain.UserGuide;
import com.linkapital.core.services.security.datasource.domain.UserTemp;
import com.linkapital.core.services.shared.contract.to.AddressTO;
import com.linkapital.core.services.shared.datasource.domain.Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:17-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class UserBinderImpl implements UserBinder {

    @Override
    public User set(UpdateUserTO source, User target) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setName( source.getName() );
        target.setCodeCountryPhone( source.getCodeCountryPhone() );
        target.setPhone( source.getPhone() );

        target.setCompletePhone( source.getCodeCountryPhone() + source.getPhone() );

        return target;
    }

    @Override
    public User bind(UserTemp source) {
        if ( source == null ) {
            return null;
        }

        User user = new User();

        user.setName( source.getName() );
        user.setEmail( source.getEmail() );
        user.setPassword( source.getPassword() );
        user.setCodeCountryPhone( source.getCodeCountryPhone() );
        user.setPhone( source.getPhone() );
        user.setCompletePhone( source.getCompletePhone() );
        user.setLinkedingContact( source.getLinkedingContact() );
        user.setCpf( source.getCpf() );
        user.setCreated( source.getCreated() );
        user.setModified( source.getModified() );

        return user;
    }

    @Override
    public UserTemp set(UserTemp target, UserTemp source) {
        if ( source == null ) {
            return null;
        }

        target.setName( source.getName() );
        target.setPassword( source.getPassword() );
        target.setCodeCountryPhone( source.getCodeCountryPhone() );
        target.setPhone( source.getPhone() );
        target.setCodeConfirmation( source.getCodeConfirmation() );
        target.setLinkedingContact( source.getLinkedingContact() );
        target.setCpf( source.getCpf() );

        target.setCreated( new java.util.Date() );
        target.setModified( new java.util.Date() );
        target.setCompletePhone( source.getCodeCountryPhone() + source.getPhone() );

        return target;
    }

    @Override
    public User bind(CreateUserTO source) {
        if ( source == null ) {
            return null;
        }

        User user = new User();

        user.setName( source.getName() );
        user.setEmail( source.getEmail() );
        user.setCodeCountryPhone( source.getCodeCountryPhone() );
        user.setPhone( source.getPhone() );
        user.setCpf( source.getCpf() );
        user.setRole( roleTOToRole( source.getRole() ) );

        user.setCompletePhone( source.getCodeCountryPhone() + source.getPhone() );

        return user;
    }

    @Override
    public UserTO bind(User source) {
        if ( source == null ) {
            return null;
        }

        UserTO userTO = new UserTO();

        userTO.setId( source.getId() );
        userTO.setEmail( source.getEmail() );
        userTO.setName( source.getName() );
        userTO.setCodeCountryPhone( source.getCodeCountryPhone() );
        userTO.setPhone( source.getPhone() );
        userTO.setLinkedingContact( source.getLinkedingContact() );
        userTO.setCpf( source.getCpf() );
        userTO.setEnabled( source.isEnabled() );
        userTO.setHasRating( source.isHasRating() );
        userTO.setCreated( source.getCreated() );
        userTO.setIntensity( source.getIntensity() );
        userTO.setRole( roleToRoleTO( source.getRole() ) );
        userTO.setUserGuide( userGuideToUserGuideTO( source.getUserGuide() ) );
        userTO.setAddress( addressToAddressTO( source.getAddress() ) );
        byte[] image = source.getImage();
        if ( image != null ) {
            userTO.setImage( Arrays.copyOf( image, image.length ) );
        }

        userTO.setIdentificationState( source.getIdentification() == null ? null : source.getIdentification().getState() );

        return userTO;
    }

    @Override
    public LightUserTO bindToLightBackOffice(User source) {
        if ( source == null ) {
            return null;
        }

        LightUserTO lightUserTO = new LightUserTO();

        lightUserTO.setId( source.getId() );
        lightUserTO.setEmail( source.getEmail() );
        lightUserTO.setName( source.getName() );
        lightUserTO.setCodeCountryPhone( source.getCodeCountryPhone() );
        lightUserTO.setPhone( source.getPhone() );
        lightUserTO.setRole( roleToRoleTO( source.getRole() ) );

        return lightUserTO;
    }

    @Override
    public UserAuthenticatedTO bindAuthenticatedUser(User source) {
        if ( source == null ) {
            return null;
        }

        UserAuthenticatedTO userAuthenticatedTO = new UserAuthenticatedTO();

        userAuthenticatedTO.setId( source.getId() );
        userAuthenticatedTO.setEmail( source.getEmail() );
        userAuthenticatedTO.setName( source.getName() );
        userAuthenticatedTO.setCodeCountryPhone( source.getCodeCountryPhone() );
        userAuthenticatedTO.setPhone( source.getPhone() );
        userAuthenticatedTO.setCpf( source.getCpf() );
        userAuthenticatedTO.setHasRating( source.isHasRating() );
        userAuthenticatedTO.setRole( roleToRoleTO( source.getRole() ) );
        userAuthenticatedTO.setUserGuide( userGuideToUserGuideTO( source.getUserGuide() ) );
        userAuthenticatedTO.setAddress( addressToAddressTO( source.getAddress() ) );
        byte[] image = source.getImage();
        if ( image != null ) {
            userAuthenticatedTO.setImage( Arrays.copyOf( image, image.length ) );
        }

        userAuthenticatedTO.setIdentificationState( source.getIdentification() == null ? null : source.getIdentification().getState() );

        return userAuthenticatedTO;
    }

    @Override
    public UserTemp bind(RegisterUserTO source) {
        if ( source == null ) {
            return null;
        }

        UserTemp userTemp = new UserTemp();

        userTemp.setName( source.getName() );
        userTemp.setEmail( source.getEmail() );
        userTemp.setPassword( source.getPassword() );
        userTemp.setCodeCountryPhone( source.getCodeCountryPhone() );
        userTemp.setPhone( source.getPhone() );
        userTemp.setCpf( source.getCpf() );
        userTemp.setPartner( source.isPartner() );

        userTemp.setCompletePhone( source.getCodeCountryPhone() + source.getPhone() );

        return userTemp;
    }

    @Override
    public List<UserTO> bindToListTO(List<User> source) {
        if ( source == null ) {
            return null;
        }

        List<UserTO> list = new ArrayList<UserTO>( source.size() );
        for ( User user : source ) {
            list.add( bind( user ) );
        }

        return list;
    }

    @Override
    public List<UserActiveTO> bindToUserActiveListTO(List<User> source) {
        if ( source == null ) {
            return null;
        }

        List<UserActiveTO> list = new ArrayList<UserActiveTO>( source.size() );
        for ( User user : source ) {
            list.add( userToUserActiveTO( user ) );
        }

        return list;
    }

    @Override
    public List<UserIdentificationTO> bindToIdentificationListTO(List<User> source) {
        if ( source == null ) {
            return null;
        }

        List<UserIdentificationTO> list = new ArrayList<UserIdentificationTO>( source.size() );
        for ( User user : source ) {
            list.add( userToUserIdentificationTO( user ) );
        }

        return list;
    }

    protected Role roleTOToRole(RoleTO roleTO) {
        if ( roleTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleTO.getId() );
        role.setName( roleTO.getName() );
        role.setCode( roleTO.getCode() );
        role.setDescription( roleTO.getDescription() );
        role.setAuthority( roleTO.getAuthority() );

        return role;
    }

    protected RoleTO roleToRoleTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleTO roleTO = new RoleTO();

        roleTO.setId( role.getId() );
        roleTO.setName( role.getName() );
        roleTO.setCode( role.getCode() );
        roleTO.setDescription( role.getDescription() );
        roleTO.setAuthority( role.getAuthority() );

        return roleTO;
    }

    protected UserGuideTO userGuideToUserGuideTO(UserGuide userGuide) {
        if ( userGuide == null ) {
            return null;
        }

        UserGuideTO userGuideTO = new UserGuideTO();

        if ( userGuide.getId() != null ) {
            userGuideTO.setId( userGuide.getId() );
        }
        userGuideTO.setAddCompanyAudio( userGuide.isAddCompanyAudio() );
        userGuideTO.setAvalAudio( userGuide.isAvalAudio() );
        userGuideTO.setDiscountAudio( userGuide.isDiscountAudio() );
        userGuideTO.setReAudio( userGuide.isReAudio() );
        userGuideTO.setImAudio( userGuide.isImAudio() );
        userGuideTO.setGeneralCompany( userGuide.isGeneralCompany() );
        userGuideTO.setCompleted( userGuide.isCompleted() );
        userGuideTO.setCreditApplicationFlow( userGuide.getCreditApplicationFlow() );

        return userGuideTO;
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

    protected UserActiveTO userToUserActiveTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserActiveTO userActiveTO = new UserActiveTO();

        if ( user.getId() != null ) {
            userActiveTO.setId( user.getId() );
        }
        userActiveTO.setName( user.getName() );
        userActiveTO.setEmail( user.getEmail() );

        return userActiveTO;
    }

    protected UserIdentificationTO userToUserIdentificationTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserIdentificationTO userIdentificationTO = new UserIdentificationTO();

        userIdentificationTO.setId( user.getId() );
        userIdentificationTO.setEmail( user.getEmail() );
        userIdentificationTO.setName( user.getName() );
        userIdentificationTO.setEnabled( user.isEnabled() );
        userIdentificationTO.setCreated( user.getCreated() );
        userIdentificationTO.setIntensity( user.getIntensity() );
        userIdentificationTO.setRole( roleToRoleTO( user.getRole() ) );

        return userIdentificationTO;
    }
}
