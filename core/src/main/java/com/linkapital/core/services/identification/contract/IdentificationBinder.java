package com.linkapital.core.services.identification.contract;

import com.linkapital.core.services.identification.contract.to.IdentificationTO;
import com.linkapital.core.services.identification.datasource.domain.Identification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IdentificationBinder {

    IdentificationBinder IDENTIFICATION_BINDER = Mappers.getMapper(IdentificationBinder.class);

    IdentificationTO bind(Identification source);

}
