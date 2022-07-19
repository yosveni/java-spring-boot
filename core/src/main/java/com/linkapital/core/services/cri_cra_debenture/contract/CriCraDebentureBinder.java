package com.linkapital.core.services.cri_cra_debenture.contract;

import com.linkapital.core.services.cri_cra_debenture.contract.to.CreateCriCraDebentureTO;
import com.linkapital.core.services.cri_cra_debenture.contract.to.CriCraDebentureTO;
import com.linkapital.core.services.cri_cra_debenture.datasource.domain.CriCraDebenture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface CriCraDebentureBinder {

    CriCraDebentureBinder CRI_CRA_DEBENTURE_BINDER = Mappers.getMapper(CriCraDebentureBinder.class);

    @Mapping(target = "id", ignore = true)
    List<CriCraDebenture> bind(List<CreateCriCraDebentureTO> source);

    List<CriCraDebentureTO> bindToCriCraDebentureTO(Set<CriCraDebenture> source);

}
