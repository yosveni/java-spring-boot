package com.linkapital.core.services.shared.contract;

import com.linkapital.core.services.shared.contract.to.AddressTO;
import com.linkapital.core.services.shared.contract.to.CreateAddressTO;
import com.linkapital.core.services.shared.datasource.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressBinder {

    AddressBinder ADDRESS_BINDER = Mappers.getMapper(AddressBinder.class);

    AddressTO bind(Address source);

    Address bind(CreateAddressTO source);

}
