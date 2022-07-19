package com.linkapital.core.services.partner_bank.contract;

import com.linkapital.core.services.partner_bank.contract.to.create.CreatePartnerBankTO;
import com.linkapital.core.services.partner_bank.contract.to.get.PartnerBankTO;
import com.linkapital.core.services.partner_bank.contract.to.update.UpdatePartnerBankTO;
import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PartnerBankBinder {

    PartnerBankBinder PARTNER_BANK_BINDER = Mappers.getMapper(PartnerBankBinder.class);

    PartnerBankTO bind(PartnerBank source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "bankNomenclatures", ignore = true)
    PartnerBank bind(CreatePartnerBankTO source);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "bankNomenclatures", ignore = true)
    PartnerBank bind(@MappingTarget PartnerBank target, UpdatePartnerBankTO source);

    List<PartnerBankTO> bind(List<PartnerBank> source);

}
