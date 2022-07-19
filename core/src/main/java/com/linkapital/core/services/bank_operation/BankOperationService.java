package com.linkapital.core.services.bank_operation;

import com.linkapital.core.services.bank_operation.datasource.domain.BankOperation;

import java.util.List;

/**
 * Default interface for {@link BankOperationService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link BankOperation}
 *
 * @since 0.0.1
 */
public interface BankOperationService {

    /**
     * Given the company's cnpj, it performs searches in the BNDES API to obtain the data referring to the company's
     * bank disbursement.
     *
     * @param cnpj {@link String} the company cnpj
     * @return {@link List}<{@link BankOperation}> the company's bank operations list
     */
    List<BankOperation> getBankOperations(String cnpj);

}
