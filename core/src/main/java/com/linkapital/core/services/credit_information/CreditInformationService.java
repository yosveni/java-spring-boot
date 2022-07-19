package com.linkapital.core.services.credit_information;

import com.linkapital.core.exceptions.CnpjNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.credit_information.datasource.domain.CreditInformation;

import java.util.List;

/**
 * Has the responsibility of search the credit information.
 */
public interface CreditInformationService {

    /**
     * Search the Credit Information from SRC API
     *
     * @param cnpj {@link String} the company cnpj.
     * @return {@link List} <{@link CreditInformation}>
     * @throws CnpjNotFoundException        if do not exist Company CNPJ.
     * @throws UnprocessableEntityException if an error occurs during the search for credit information.
     */
    List<CreditInformation> getCnpjCreditInformationData(String cnpj) throws CnpjNotFoundException, UnprocessableEntityException;

}
