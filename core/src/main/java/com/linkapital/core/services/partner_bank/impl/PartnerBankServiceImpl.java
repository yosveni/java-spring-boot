package com.linkapital.core.services.partner_bank.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.datasource.BankNomenclatureRepository;
import com.linkapital.core.services.partner_bank.PartnerBankService;
import com.linkapital.core.services.partner_bank.contract.to.create.CreatePartnerBankTO;
import com.linkapital.core.services.partner_bank.contract.to.get.PartnerBankTO;
import com.linkapital.core.services.partner_bank.contract.to.update.UpdatePartnerBankTO;
import com.linkapital.core.services.partner_bank.datasource.PartnerBankRepository;
import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.partner_bank.contract.PartnerBankBinder.PARTNER_BANK_BINDER;

@Service
@Transactional
public class PartnerBankServiceImpl implements PartnerBankService {

    private final PartnerBankRepository partnerBankRepository;
    private final BankNomenclatureRepository bankNomenclatureRepository;
    private final PartnerBankValidator partnerBankValidator;

    public PartnerBankServiceImpl(PartnerBankRepository partnerBankRepository,
                                  BankNomenclatureRepository bankNomenclatureRepository,
                                  PartnerBankValidator partnerBankValidator) {
        this.partnerBankRepository = partnerBankRepository;
        this.bankNomenclatureRepository = bankNomenclatureRepository;
        this.partnerBankValidator = partnerBankValidator;
    }

    @Override
    public PartnerBankTO create(CreatePartnerBankTO to) throws UnprocessableEntityException {
        partnerBankValidator.validateNameExist(to.getName());
        return save(PARTNER_BANK_BINDER.bind(to), to.getBankNomenclatures());
    }

    @Override
    public PartnerBankTO update(UpdatePartnerBankTO to) throws UnprocessableEntityException {
        var partnerBank = getById(to.getId());
        partnerBankValidator.validatePartnerBaseUpdate(partnerBank.getName(), to.getName());
        partnerBankValidator.validateNameExistUpdate(to.getId(), to.getName());

        return save(PARTNER_BANK_BINDER.bind(partnerBank, to), to.getBankNomenclatures());
    }

    @Override
    public PartnerBank getById(Long id) throws UnprocessableEntityException {
        return partnerBankRepository.findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("partner.bank.not.found", id)));
    }

    @Override
    public List<PartnerBankTO> getAll() {
        return PARTNER_BANK_BINDER.bind(partnerBankRepository.findAll());
    }

    @Override
    public void delete(Long id) throws UnprocessableEntityException {
        var partnerBank = getById(id);
        partnerBankValidator.validatePartnerBaseName(partnerBank.getName());
        partnerBankRepository.delete(partnerBank);
    }

    //region Search for past bank nomenclature in id list and then save partner bank with found bank nomenclature
    private PartnerBankTO save(PartnerBank partnerBank,
                               List<Long> bankNomenclaturesIds) {
        var bankNomenclatures = bankNomenclatureRepository.findAllByIdIn(bankNomenclaturesIds);
        partnerBank = partnerBank.withBankNomenclatures(bankNomenclatures);

        return PARTNER_BANK_BINDER.bind(partnerBankRepository.save(partnerBank));
    }
    //endregion

}
