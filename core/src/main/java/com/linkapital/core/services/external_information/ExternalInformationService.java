package com.linkapital.core.services.external_information;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_operation.contract.to.BankOperationTO;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.ibge.contract.to.IbgeTO;
import com.linkapital.core.services.industrial_production_index.contract.to.PhysicalProductionTO;
import com.linkapital.core.services.protest.contract.to.ProtestInformationTO;
import com.linkapital.core.services.shared.datasource.domain.Address;
import lombok.NonNull;

import java.util.List;

/**
 * The interface External information service.
 */
public interface ExternalInformationService {

    /**
     * Update protest information.
     *
     * @param cnpj {@link String} the cnpj
     * @return {@link  ProtestInformationTO}
     * @throws UnprocessableEntityException if any error occurs.
     */
    ProtestInformationTO updateProtestInformation(String cnpj) throws UnprocessableEntityException;

    /**
     * Fill location.
     *
     * @param companyAddress {@link Address} the address
     */
    void populateLocation(Address companyAddress);

    /**
     * Populate external information.
     *
     * @param company {@link Company} the company
     * @throws UnprocessableEntityException if any error occurs.
     */
    void populateExternalInformation(@NonNull Company company) throws UnprocessableEntityException;

    /**
     * Make the call to the api in charge of processing the data the geographical statistics of the company.
     *
     * @param company {@link Company} the company
     */
    void populateIbge(@NonNull Company company);

    /**
     * Make the call to the api in charge of processing the data the bank operations of the company
     *
     * @param company {@link Company} the company
     */
    void populateBankOperations(@NonNull Company company);

    /**
     * Populate physical production.
     *
     * @param company {@link Company} the company
     */
    void populatePhysicalProduction(@NonNull Company company);

    /**
     * Make the call to the api in charge of processing the data the bank operations of the company.
     *
     * @param company {@link Company} the company
     */
    void populateProtests(@NonNull Company company) throws UnprocessableEntityException;

    /**
     * Populate cnd documents.
     *
     * @param company {@link Company} the company
     */
    void populateCndDocuments(@NonNull Company company);

    /**
     * Upload documents for the given company (ENVIRONMENT PROD) If the current profile is not production or
     * company social reason don't has text then return the company without any updated Else retrieve from the bot
     * the Registration form, simplified certification and the archived documents by the company social reason
     *
     * @param company {@link Company} the company
     * @return {@link Company} the company
     * @throws UnprocessableEntityException if any error occurs.
     */
    Company uploadJucespDocuments(@NonNull Company company) throws UnprocessableEntityException;

    /**
     * Get the archived documents from the JUCESP API
     *
     * @param cnpj {@link String} The company cnpj tu generate documents
     * @return {@link List}<{@link DirectoryTO}> the directory list
     * @throws UnprocessableEntityException if any error occurs.
     */
    List<DirectoryTO> generateJucespDocuments(String cnpj) throws UnprocessableEntityException;

    /**
     * Update IBGE
     *
     * @param cnpj {@link String} the company cnpj
     * @return {@link IbgeTO}
     * @throws UnprocessableEntityException if any error occurs.
     */
    IbgeTO updateIbge(String cnpj) throws UnprocessableEntityException;

    /**
     * Update Bank Operations
     *
     * @param cnpj {@link String} the company cnpj
     * @return {@link List}<{@link BankOperationTO}>
     * @throws UnprocessableEntityException if any error occurs.
     */
    List<BankOperationTO> updateBankOperations(String cnpj) throws UnprocessableEntityException;

    /**
     * Update Physical Productions
     *
     * @param cnpj {@link String} the company cnpj
     * @return {@link List}<{@link PhysicalProductionTO}>
     * @throws UnprocessableEntityException if any error occurs.
     */
    List<PhysicalProductionTO> updatePhysicalProduction(String cnpj) throws UnprocessableEntityException;

}
