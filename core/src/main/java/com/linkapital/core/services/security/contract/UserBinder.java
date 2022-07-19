package com.linkapital.core.services.security.contract;

import com.linkapital.core.services.security.contract.to.LightUserTO;
import com.linkapital.core.services.security.contract.to.RegisterUserTO;
import com.linkapital.core.services.security.contract.to.UserActiveTO;
import com.linkapital.core.services.security.contract.to.UserAuthenticatedTO;
import com.linkapital.core.services.security.contract.to.UserIdentificationTO;
import com.linkapital.core.services.security.contract.to.UserTO;
import com.linkapital.core.services.security.contract.to.create.CreateUserTO;
import com.linkapital.core.services.security.contract.to.update.UpdateUserTO;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.services.security.datasource.domain.UserTemp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserBinder {

    UserBinder USER_BINDER = Mappers.getMapper(UserBinder.class);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "confirmToken", ignore = true)
    @Mapping(target = "codeConfirmation", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "authoritiesValues", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "hasRating", ignore = true)
    @Mapping(target = "initChangePassword", ignore = true)
    @Mapping(target = "userGuide", ignore = true)
    @Mapping(target = "identification", ignore = true)
    @Mapping(target = "intensity", ignore = true)
    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "campaignUsers", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "completePhone", expression = "java(source.getCodeCountryPhone() + source.getPhone())")
    User set(UpdateUserTO source, @MappingTarget User target);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "confirmToken", ignore = true)
    @Mapping(target = "codeConfirmation", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "authoritiesValues", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "hasRating", ignore = true)
    @Mapping(target = "initChangePassword", ignore = true)
    @Mapping(target = "userGuide", ignore = true)
    @Mapping(target = "identification", ignore = true)
    @Mapping(target = "intensity", ignore = true)
    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "campaignUsers", ignore = true)
    User bind(UserTemp source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "partner", ignore = true)
    @Mapping(target = "created", expression = "java(new java.util.Date())")
    @Mapping(target = "modified", expression = "java(new java.util.Date())")
    @Mapping(target = "completePhone", expression = "java(source.getCodeCountryPhone() + source.getPhone())")
    UserTemp set(@MappingTarget UserTemp target, UserTemp source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "linkedingContact", ignore = true)
    @Mapping(target = "confirmToken", ignore = true)
    @Mapping(target = "codeConfirmation", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "authoritiesValues", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "hasRating", ignore = true)
    @Mapping(target = "initChangePassword", ignore = true)
    @Mapping(target = "userGuide", ignore = true)
    @Mapping(target = "identification", ignore = true)
    @Mapping(target = "intensity", ignore = true)
    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "campaignUsers", ignore = true)
    @Mapping(target = "completePhone", expression = "java(source.getCodeCountryPhone() + source.getPhone())")
    User bind(CreateUserTO source);

    @Mapping(target = "identificationState", expression = "java(source.getIdentification() == null ? null : source.getIdentification().getState())")
    UserTO bind(User source);

    LightUserTO bindToLightBackOffice(User source);

    @Mapping(target = "token", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "identificationState", expression = "java(source.getIdentification() == null ? null : source.getIdentification().getState())")
    UserAuthenticatedTO bindAuthenticatedUser(User source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codeConfirmation", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "completePhone", expression = "java(source.getCodeCountryPhone() + source.getPhone())")
    UserTemp bind(RegisterUserTO source);

    @Mapping(target = "identificationState", expression = "java(source.getIdentification() == null ? null : source.getIdentification().getState())")
    List<UserTO> bindToListTO(List<User> source);

    List<UserActiveTO> bindToUserActiveListTO(List<User> source);

    List<UserIdentificationTO> bindToIdentificationListTO(List<User> source);

    default User bindUser(UserTemp source) {
        var user = bind(source);
        user.setEnabled(true);
        user.setCreated(new Date());
        user.setModified(new Date());

        return user;
    }

}
