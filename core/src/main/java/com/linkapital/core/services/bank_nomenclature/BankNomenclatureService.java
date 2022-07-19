package com.linkapital.core.services.bank_nomenclature;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.contract.to.create.CreateBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.update.UpdateBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.offer.datasource.domain.Offer;

import java.util.List;

/**
 * Default interface for {@link BankNomenclatureService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link BankNomenclature}
 *
 * @since 0.0.1
 */
public interface BankNomenclatureService {

    /**
     * Save a new bank nomenclature
     *
     * @param to {@link CreateBankNomenclatureTO} the data to create the bank nomenclature
     * @return {@link BankNomenclatureTO}
     */
    BankNomenclatureTO create(CreateBankNomenclatureTO to) throws UnprocessableEntityException;

    /**
     * Update the bank nomenclature
     *
     * @param to {@link UpdateBankNomenclatureTO} the data to update the bank nomenclature
     * @return {@link BankNomenclatureTO}
     * @throws UnprocessableEntityException if not found a {@link BankNomenclature} with the given id
     */
    BankNomenclatureTO update(UpdateBankNomenclatureTO to) throws UnprocessableEntityException;

    /**
     * Get a bank nomenclature by id
     *
     * @param id {@link Long} the bank nomenclature id
     * @return {@link BankNomenclature}
     * @throws UnprocessableEntityException if not found a {@link BankNomenclature} with the given id
     */
    BankNomenclature getById(Long id) throws UnprocessableEntityException;

    /**
     * Get all bank nomenclatures
     *
     * @return {@link List}<{@link BankNomenclatureTO}>
     */
    List<BankNomenclatureTO> getAll();

    /**
     * Get all the bank nomenclature for the company
     *
     * @return {@link List}<{@link CompanyBankNomenclatureTO}>
     */
    List<CompanyBankNomenclatureTO> getAllForCompany();

    /**
     * Get all the bank nomenclature for the company by partner bank name
     *
     * @return {@link List}<{@link CompanyBankNomenclatureTO}>
     */
    List<CompanyBankNomenclatureTO> getAllByPartnerBankLinkapital();

    /**
     * Get all bank nomenclatures by offer and company
     *
     * @param offer       {@link Offer} the offer data
     * @param companyUser {@link CompanyUser} the company data
     * @return {@link List}<{@link CompanyBankNomenclatureTO}>
     */
    List<CompanyBankNomenclatureTO> getAllByOffer(Offer offer, CompanyUser companyUser);

    /**
     * Returns all the nomenclators belonging to the selected products in the accepted offer of the company
     *
     * @param companyUser {@link CompanyUser} the company user data
     * @return {@link List}<{@link CompanyBankNomenclatureTO}> the company bank nomenclatures
     */
    List<CompanyBankNomenclatureTO> getAllByCompanyUser(CompanyUser companyUser);

    /**
     * Returns all the nomenclators belonging to the selected products in the accepted offer of the company
     *
     * @param companyUser       {@link CompanyUser} the company user data
     * @param bankNomenclatures {@link List}<{@link CompanyBankNomenclatureTO}> the bank nomenclatures
     * @return {@link List}<{@link CompanyBankNomenclatureTO}> the company bank nomenclatures
     */
    List<CompanyBankNomenclatureTO> getAllByCompanyUser(CompanyUser companyUser, List<CompanyBankNomenclatureTO> bankNomenclatures);

    /**
     * Delete a bank nomenclature by id
     *
     * @param id {@link Long} the bank nomenclature id
     * @throws UnprocessableEntityException if not found a {@link BankNomenclature} with the given Id
     */
    void delete(Long id) throws UnprocessableEntityException;

}
