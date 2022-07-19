package com.linkapital.core.services.partner_bank;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.partner_bank.contract.to.create.CreatePartnerBankTO;
import com.linkapital.core.services.partner_bank.contract.to.get.PartnerBankTO;
import com.linkapital.core.services.partner_bank.contract.to.update.UpdatePartnerBankTO;
import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;

import java.util.List;

/**
 * Default interface for {@link PartnerBankService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link PartnerBank}
 *
 * @since 0.0.1
 */
public interface PartnerBankService {

    /**
     * Create a partner bank
     *
     * @param to {@link CreatePartnerBankTO} the partner bank to create
     * @return {@link PartnerBankTO}
     */
    PartnerBankTO create(CreatePartnerBankTO to) throws UnprocessableEntityException;

    /**
     * Update a partner bank
     *
     * @param to {@link UpdatePartnerBankTO} the partner bank to update
     * @return {@link PartnerBankTO}
     * @throws UnprocessableEntityException if not found a {@link PartnerBank} by id
     */
    PartnerBankTO update(UpdatePartnerBankTO to) throws UnprocessableEntityException;

    /**
     * Get partner bank by id
     *
     * @param id {@link Long} the partner bank id
     * @return {@link PartnerBank}
     * @throws UnprocessableEntityException if not found a {@link PartnerBank} by id
     */
    PartnerBank getById(Long id) throws UnprocessableEntityException;

    /**
     * List all partner bank.
     *
     * @return {@link List}<{@link PartnerBankTO}>
     */
    List<PartnerBankTO> getAll();

    /**
     * Deleting a partner bank by id.
     *
     * @param id {@link Long} the partner bank id
     * @throws UnprocessableEntityException if the partner bank cannot be removed
     */
    void delete(Long id) throws UnprocessableEntityException;

}
