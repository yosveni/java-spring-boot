package com.linkapital.core.services.partner_bank.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.partner_bank.datasource.PartnerBankRepository;
import org.springframework.stereotype.Service;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

@Service
public class PartnerBankValidator {

    private static final String linkapital = "LINKAPITAL";
    private final PartnerBankRepository partnerBankRepository;

    public PartnerBankValidator(PartnerBankRepository partnerBankRepository) {
        this.partnerBankRepository = partnerBankRepository;
    }

    public void validatePartnerBaseUpdate(String currentName, String newName) throws UnprocessableEntityException {
        if (currentName.equals(linkapital) && !newName.equals(linkapital))
            throw new UnprocessableEntityException(msg("partner.bank.operation.denied", linkapital));
    }

    public void validatePartnerBaseName(String name) throws UnprocessableEntityException {
        if (name.equals(linkapital))
            throw new UnprocessableEntityException(msg("partner.bank.operation.denied", name));
    }

    public void validateNameExist(String name) throws UnprocessableEntityException {
        if (partnerBankRepository.existsByName(name))
            throw new UnprocessableEntityException(msg("partner.bank.name.already.exist", name));
    }

    public void validateNameExistUpdate(long id, String name) throws UnprocessableEntityException {
        if (partnerBankRepository.existsByIdIsNotAndName(id, name))
            throw new UnprocessableEntityException(msg("partner.bank.name.already.exist", name));
    }

}
