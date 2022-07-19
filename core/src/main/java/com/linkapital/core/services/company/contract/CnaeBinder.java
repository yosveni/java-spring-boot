package com.linkapital.core.services.company.contract;

import com.linkapital.core.services.company.contract.to.CnaeTO;
import com.linkapital.core.services.company.datasource.domain.Cnae;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CnaeBinder {

    CnaeBinder CNAE_BINDER = Mappers.getMapper(CnaeBinder.class);

    CnaeTO bind(Cnae source);

}
