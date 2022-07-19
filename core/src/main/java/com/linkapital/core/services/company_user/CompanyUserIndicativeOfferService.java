package com.linkapital.core.services.company_user;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankDocumentTO;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.CompanyConfirm1TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning1TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning2TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning3TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning4TO;
import com.linkapital.core.services.company_user.contract.to.CompanySpedTO;
import com.linkapital.core.services.company_user.contract.to.DataInitLearning2TO;
import com.linkapital.core.services.company_user.contract.to.GenericTO;
import com.linkapital.core.services.company_user.contract.to.InitLearningFourTO;
import com.linkapital.core.services.company_user.contract.to.InitLearningOneTO;
import com.linkapital.core.services.company_user.contract.to.InitLearningThreeTO;
import com.linkapital.core.services.company_user.contract.to.InitLearningTwoTO;
import com.linkapital.core.services.company_user.contract.to.LearningConfirmTO;
import com.linkapital.core.services.company_user.contract.to.UpdateLearningFourTO;
import com.linkapital.core.services.company_user.contract.to.UpdateLearningThreeTO;
import com.linkapital.core.services.sped.contract.to.CreateSpedTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Default interface for {@link CompanyUserIndicativeOfferService}
 * Service with the responsibility of carrying out operations between the company and the indicative offers
 *
 * @since 0.0.1
 */
public interface CompanyUserIndicativeOfferService {

    /**
     * Init learning 1, add to a company credit and invoicing data and set the given user as a client of the given cnpj
     * company
     *
     * @param to {@link InitLearningOneTO} the data to start learning 1
     * @return {@link CompanyLearning1TO}
     * @throws UnprocessableEntityException if error occurs
     */
    CompanyLearning1TO initLearningOne(InitLearningOneTO to) throws UnprocessableEntityException;

    /**
     * Update indicative offer one
     *
     * @param to {@link InitLearningOneTO} the data to update learning one
     * @return {@link CompanyLearning1TO}
     * @throws UnprocessableEntityException if error occurs
     */
    CompanyLearning1TO updateLearningOne(InitLearningOneTO to) throws UnprocessableEntityException;

    /**
     * Init learning 2, given the cnpj, IRPF file and IRPF Receipt, the information
     * of the properties of the company and its partners searched by their CPF is returned to the client
     *
     * @param to {@link InitLearningTwoTO} the data to start learning offer two
     * @return {@link CompanyLearning2TO} the CompanyLearning2TO entity
     * @throws ResourceNotFoundException    if any error occur applying a converter or learning offer converter to the
     *                                      company
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyLearning2TO initLearningTwo(InitLearningTwoTO to) throws ResourceNotFoundException, UnprocessableEntityException;

    /**
     * Update learning 2, given the cnpj, IRPF file and IRPF Receipt, the information
     * of the properties of the company and its partners searched by their CPF is returned to the client
     *
     * @param to {@link InitLearningTwoTO} the data to start learning offer two
     * @return {@link CompanyLearning2TO} the CompanyLearning2TO entity
     * @throws ResourceNotFoundException    if any error occur applying a converter or learning offer converter to the
     *                                      company
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyLearning2TO updateLearningTwo(InitLearningTwoTO to) throws ResourceNotFoundException, UnprocessableEntityException;

    /**
     * Receives the invoices issued and received, they are stored in the database and an analysis performed on these
     * invoices is returned
     *
     * @param to {@link InitLearningThreeTO} the data to start the learning offer three
     * @return {@link CompanyLearning3TO} the data from the invoice analysis
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyLearning3TO initLearningThree(InitLearningThreeTO to) throws UnprocessableEntityException;

    /**
     * Update indicative offer three
     *
     * @param to {@link UpdateLearningThreeTO} the data to update the learning offer three
     * @return {@link CompanyLearning3TO} the data from the invoice analysis
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyLearning3TO updateLearningThree(UpdateLearningThreeTO to) throws UnprocessableEntityException;

    /**
     * The data necessary to start the indicative offer four
     *
     * @param to {@link InitLearningFourTO} the data to create the SPED record
     * @return {@link CompanySpedTO}
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanySpedTO initLearningFour(InitLearningFourTO to) throws UnprocessableEntityException;

    /**
     * Update indicative offer four
     *
     * @param to {@link UpdateLearningFourTO} the data to update learning offer four.
     * @return {@link CompanySpedTO}
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanySpedTO updateLearningFour(UpdateLearningFourTO to) throws UnprocessableEntityException;

    /**
     * Upload sped files in format txt
     *
     * @param cnpj   {@link String} the cnpj of the company
     * @param userId {@link Long} the user id
     * @param files  {@link MultipartFile}[] the speds files
     * @return <{@link CompanyBankDocumentTO}>
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyLearning4TO uploadTxtSped(String cnpj, Long userId, MultipartFile[] files) throws UnprocessableEntityException;

    /**
     * Upload sped files in format excel
     *
     * @param cnpj   {@link String} the cnpj of the company
     * @param userId {@link Long} the user id
     * @param file   {@link MultipartFile} the excel sped data
     * @return <{@link CompanyBankDocumentTO}>
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyLearning4TO uploadExcelSped(String cnpj, Long userId, MultipartFile file) throws UnprocessableEntityException;

    /**
     * Receive the sped registration data pertaining to the missing years to store them in the database
     *
     * @param to {@link CreateSpedTO} the data to create the SPED record
     * @return {@link CompanyLearning4TO}the sped analysis list
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyLearning4TO analysisLearningFour(CreateSpedTO to) throws UnprocessableEntityException;

    /**
     * Confirm information in learning one
     *
     * @param to {@link CompanyConfirm1TO} the data to confirm information of indicative offer one
     * @return {@link CompanyClientTO} the CompanyClientTO with the latest registration form field updated
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyClientTO confirmLearningOne(CompanyConfirm1TO to) throws UnprocessableEntityException;

    /**
     * Confirm information in learning two
     *
     * @param to {@link LearningConfirmTO} the data to confirm information of indicative offer two
     * @return {@link CompanyClientTO} the CompanyClientTO with the latest registration form field updated
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyClientTO confirmLearningTwo(LearningConfirmTO to) throws UnprocessableEntityException;

    /**
     * Confirm information in learning three
     *
     * @param to {@link LearningConfirmTO} the data to confirm information of indicative offer three
     * @return {@link CompanyClientTO} the CompanyClientTO with the latest registration form field updated
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyClientTO confirmLearningThree(LearningConfirmTO to) throws UnprocessableEntityException;

    /**
     * Confirm information in learning four
     *
     * @param to {@link LearningConfirmTO} the data to confirm information of indicative offer four
     * @return {@link CompanyClientTO} the CompanyClientTO with the latest registration form field updated
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyClientTO confirmLearningFour(LearningConfirmTO to) throws UnprocessableEntityException;

    /**
     * Get selected partners in init learning 2 and company partners
     *
     * @param cnpj the {@link String} cnpj company
     * @return the {@link InitLearningTwoTO} init learning two
     * @throws ResourceNotFoundException    if not found a {@link Company} entity in datasource by the given cnpj code
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    DataInitLearning2TO getInitLearningTwo(String cnpj) throws ResourceNotFoundException, UnprocessableEntityException;

    /**
     * Get the result of the analysis according to the indicative offer number
     *
     * @param cnpj                {@link String} the cnpj of the company
     * @param userId              {@link Long} the user id
     * @param indicativeOfferType {@link Integer} the indicative offer type.
     * @return the {@link GenericTO} the data of the analysis
     * @throws ResourceNotFoundException if error occurs
     */
    GenericTO getIndicativeOfferAnalysis(String cnpj, Long userId, int indicativeOfferType) throws ResourceNotFoundException;

}
