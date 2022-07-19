package com.linkapital.core.services.bank_nomenclature.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.BankNomenclatureService;
import com.linkapital.core.services.bank_nomenclature.contract.to.create.CreateBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.update.UpdateBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.datasource.BankNomenclatureRepository;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import com.linkapital.core.services.partner_bank.datasource.PartnerBankRepository;
import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.bank_nomenclature.contract.BankNomenclatureBinder.BANK_NOMENCLATURE_BINDER;
import static com.linkapital.core.services.bank_nomenclature.contract.BankNomenclatureBinder.filterBankNomenclature;
import static com.linkapital.core.services.bank_nomenclature.contract.BankNomenclatureBinder.getAreas;
import static com.linkapital.core.services.bank_nomenclature.validator.BankNomenclatureValidator.checkDuplicatedUrgency;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;

@Service
@Transactional
public class BankNomenclatureServiceImpl implements BankNomenclatureService {

    private final BankNomenclatureRepository bankNomenclatureRepository;
    private final PartnerBankRepository partnerBankRepository;

    public BankNomenclatureServiceImpl(BankNomenclatureRepository bankNomenclatureRepository,
                                       PartnerBankRepository partnerBankRepository) {
        this.bankNomenclatureRepository = bankNomenclatureRepository;
        this.partnerBankRepository = partnerBankRepository;
    }

    @Override
    public BankNomenclatureTO create(CreateBankNomenclatureTO to) throws UnprocessableEntityException {
        var bankNomenclature = BANK_NOMENCLATURE_BINDER.bind(to);
        var partnersBanks = partnerBankRepository.findAllByIdIn(to.getPartnersBank());

        return BANK_NOMENCLATURE_BINDER.bind(save(bankNomenclature, partnersBanks));
    }

    @Override
    public BankNomenclatureTO update(@NonNull UpdateBankNomenclatureTO to) throws UnprocessableEntityException {
        var bankNomenclature = getById(to.getId());
        var partnersBanks = partnerBankRepository.findAllByIdIn(to.getPartnersBank());
        bankNomenclature = BANK_NOMENCLATURE_BINDER.set(to, bankNomenclature);

        return BANK_NOMENCLATURE_BINDER.bind(save(bankNomenclature, partnersBanks));
    }

    @Override
    public BankNomenclature getById(Long id) throws UnprocessableEntityException {
        return bankNomenclatureRepository
                .findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("bank.nomenclature.not.found", id)));
    }

    @Override
    public List<BankNomenclatureTO> getAll() {
        return BANK_NOMENCLATURE_BINDER.bind(bankNomenclatureRepository.findAll());
    }

    @Override
    public List<CompanyBankNomenclatureTO> getAllForCompany() {
        return BANK_NOMENCLATURE_BINDER.bindCompanyBankNomenclatureTO(bankNomenclatureRepository.findAll());
    }

    @Override
    public List<CompanyBankNomenclatureTO> getAllByPartnerBankLinkapital() {
        return BANK_NOMENCLATURE_BINDER.bindCompanyBankNomenclatureTO(
                bankNomenclatureRepository.findAllByPartnerBankLinkapital());
    }

    @Override
    public List<CompanyBankNomenclatureTO> getAllByOffer(@NonNull Offer offer, CompanyUser companyUser) {
        var nomenclatures = offer.getPartnerBank().getBankNomenclatures();
        if (nomenclatures.isEmpty())
            return new ArrayList<>();

        var bankNomenclatures = BANK_NOMENCLATURE_BINDER.bindCompanyBankNomenclatureTO(
                nomenclatures);

        return filterBankNomenclature.apply(singletonList(offer.getType()), bankNomenclatures,
                companyUser.getBankDocuments());
    }

    @Override
    public void delete(Long id) throws UnprocessableEntityException {
        bankNomenclatureRepository.delete(getById(id));
    }

    @Override
    public List<CompanyBankNomenclatureTO> getAllByCompanyUser(CompanyUser companyUser) {
        var areas = getAreas.apply(companyUser);
        if (areas.isEmpty())
            return new ArrayList<>();

        var bankNomenclatures = getAllByPartnerBankLinkapital();
        if (bankNomenclatures.isEmpty())
            return new ArrayList<>();

        return filterBankNomenclature.apply(areas, bankNomenclatures, companyUser.getBankDocuments());
    }

    @Override
    public List<CompanyBankNomenclatureTO> getAllByCompanyUser(CompanyUser companyUser,
                                                               @NonNull List<CompanyBankNomenclatureTO> bankNomenclatures) {
        if (bankNomenclatures.isEmpty())
            return new ArrayList<>();

        var areas = getAreas.apply(companyUser);

        return areas.isEmpty()
                ? new ArrayList<>()
                : filterBankNomenclature.apply(areas, bankNomenclatures, companyUser.getBankDocuments());
    }

    //region Search for past bank partners in id list and then save bank nomenclators with found bank partners
    private @NonNull BankNomenclature save(@NonNull BankNomenclature bankNomenclature,
                                           List<PartnerBank> partnersBank) throws UnprocessableEntityException {
        checkDuplicatedUrgency(bankNomenclature.getNomenclatureUrgencies());

        var urgencies = bankNomenclature.getNomenclatureUrgencies()
                .stream()
                .map(bankNomenclatureUrgency -> bankNomenclatureUrgency.withBankNomenclature(bankNomenclature))
                .collect(toSet());
        bankNomenclature.getNomenclatureUrgencies().addAll(urgencies);

        var partnerBankList = bankNomenclature.getPartnersBank();
        partnerBankList.forEach(partnerBank -> partnerBank.getBankNomenclatures().remove(bankNomenclature));
        partnerBankRepository.saveAll(partnerBankList);

        return bankNomenclatureRepository.save(bankNomenclature.withPartnersBank(partnersBank));
    }
    //endregion

}
