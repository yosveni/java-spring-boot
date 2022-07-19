package com.linkapital.core.services.security.contract;

import com.linkapital.core.services.security.contract.to.UserTempTO;
import com.linkapital.core.services.security.datasource.domain.UserTemp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserTempBinder {

    UserTempBinder USER_TEMP_BINDER = Mappers.getMapper(UserTempBinder.class);

    UserTempTO bind(UserTemp source);

    List<UserTempTO> bind(List<UserTemp> source);

}
