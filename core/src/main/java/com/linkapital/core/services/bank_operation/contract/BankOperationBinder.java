package com.linkapital.core.services.bank_operation.contract;

import com.linkapital.core.services.bank_operation.contract.to.BankOperationTO;
import com.linkapital.core.services.bank_operation.datasource.domain.BankOperation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface BankOperationBinder {

    BankOperationBinder BANK_OPERATION_BINDER = Mappers.getMapper(BankOperationBinder.class);

    List<BankOperationTO> bind(Collection<BankOperation> source);

}
