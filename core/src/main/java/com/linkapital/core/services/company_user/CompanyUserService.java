package com.linkapital.core.services.company_user;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankDocumentTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.update.UpdateBankDocumentTO;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import com.linkapital.core.services.company.contract.to.CompanyTO;
import com.linkapital.core.services.company.contract.to.DocumentCompanyTO;
import com.linkapital.core.services.company.contract.to.LearningSessionConfirmTO;
import com.linkapital.core.services.company.contract.to.SessionConfirmedTO;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.CompanyTransferTO;
import com.linkapital.core.services.company_user.contract.to.LightBackOfficeTO;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.method_k.contract.to.ScoreSummaryTO;
import com.linkapital.core.services.security.datasource.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Default interface for {@link CompanyUserService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link CompanyUser}
 *
 * @since 0.0.1
 */
public interface CompanyUserService {

    /**
     * Save the given company in the datasource
     *
     * @param companyUser {@link CompanyUser} the company to be register in datasource
     * @return {@link Company} the company entity saved in datasource
     */
    CompanyUser save(CompanyUser companyUser);

    /**
     * Save to backoffice the cnpj codes found in the given {@link MultipartFile}
     *
     * @param file {@link MultipartFile} a file to extract the cnpj codes
     * @return {@link List}<{@link LightBackOfficeTO}>
     * @throws UnprocessableEntityException if error occurs
     */
    List<LightBackOfficeTO> saveCnpjsFile(MultipartFile file) throws UnprocessableEntityException;

    /**
     * Save backoffice to the given cnpj list
     *
     * @param cnpjList {@link List}<{@link String}> a list with the cnpj of the companies to be register
     * @return {@link List}<{@link LightBackOfficeTO}>
     * @throws UnprocessableEntityException if error occurs
     */
    List<LightBackOfficeTO> saveCnpjList(List<String> cnpjList) throws UnprocessableEntityException;

    /**
     * Confirm information in learning 2
     *
     * @param to {@link LearningSessionConfirmTO}
     * @return {@link SessionConfirmedTO}
     * @throws UnprocessableEntityException if error occurs
     */
    SessionConfirmedTO confirmLearningSession(LearningSessionConfirmTO to) throws UnprocessableEntityException;

    /**
     * Returns the data of a company given the cnpj and the id of the user
     *
     * @param cnpj   {@link String} the cnpj of the company
     * @param userId {@link Long} the user id
     * @return {@link CompanyUser} the learning data of company found in datasource
     * @throws UnprocessableEntityException if error occurs
     */
    CompanyUser get(String cnpj, Long userId) throws UnprocessableEntityException;

    /**
     * Returns the data of a company given the cnpj and the current authenticated user's id.
     *
     * @param cnpj {@link String} the cnpj of the company
     * @return {@link CompanyUser} the learning data of company found in datasource
     * @throws UnprocessableEntityException if error occurs
     */
    default CompanyUser get(String cnpj) throws UnprocessableEntityException {
        return get(cnpj, ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    /**
     * Returns all the documents of a company given its cpj and a user id, if the user id is not specified, the id of
     * the authenticated user is chosen
     *
     * @param cnpj   {@link String} the company cnpj
     * @param userId {@link Long} the user id
     * @return {@link DocumentCompanyTO} the document company
     * @throws UnprocessableEntityException If a {@link CompanyUser} is not found by cnpj and user id
     */
    DocumentCompanyTO getDocumentsCompany(String cnpj, Long userId) throws UnprocessableEntityException;

    /**
     * Returns the data of a company given the cnpj and the id of the user
     *
     * @param cnpj   {@link String} the cnpj of the company
     * @param userId {@link Long} the user id
     * @return {@link CompanyTO} the learning data of company found in datasource
     * @throws ResourceNotFoundException if error occurs
     */
    CompanyTO getRelation(String cnpj, Long userId) throws ResourceNotFoundException;

    /**
     * Returns the result of the credit analysis score
     *
     * @param cnpj   {@link String} the cnpj of the company
     * @param userId {@link Long} the user id
     * @return {@link ScoreSummaryTO} the learning data of company found in datasource
     * @throws ResourceNotFoundException if error occurs
     */
    ScoreSummaryTO getScoreAnalysis(String cnpj, Long userId) throws ResourceNotFoundException;

    /**
     * Get the sessions confirmed of the company by cnpj
     *
     * @param cnpj {@link String} The company cnpj tu generate documents
     * @return {@link SessionConfirmedTO} the sessions confirmed of the company
     * @throws ResourceNotFoundException if error occurs
     */
    SessionConfirmedTO getSessionConfirmed(String cnpj) throws ResourceNotFoundException;

    /**
     * Returns a company client of the user of the logged-in user and given a cnpj
     *
     * @param cnpj {@link String} the company cnpj
     * @return {@link CompanyClientTO}
     * @throws ResourceNotFoundException if error occurs
     */
    CompanyClientTO getCompanyClient(String cnpj) throws ResourceNotFoundException;

    /**
     * Transfer the company to another user.
     *
     * @param to {@link CompanyTransferTO}
     * @return {@link LightBackOfficeTO}
     * @throws UnprocessableEntityException if error occurs
     */
    LightBackOfficeTO transferCompanyToUser(CompanyTransferTO to) throws UnprocessableEntityException;

    /**
     * Update the bank document state
     *
     * @param to {@link UpdateBankDocumentTO} the data to update the bank document
     * @return {@link CompanyBankDocumentTO}
     * @throws UnprocessableEntityException if not found a {@link BankNomenclature} with the given id
     */
    CompanyBankDocumentTO updateBankDocumentState(UpdateBankDocumentTO to) throws UnprocessableEntityException;

    /**
     * Get all the companies belongs to the user cnpj code, and has the client column in true
     *
     * @return {@link List}<{@link CompanyClientTO}>
     */
    List<CompanyClientTO> getAllByUserClient() throws UnprocessableEntityException;

    /**
     * Upload a file correspond to the given cnpj to the file storage
     *
     * @param cnpj  {@link String} the cnpj of the company
     * @param files {@link MultipartFile}[] the array of the files to be register in the file store
     * @return {@link List}<{@link DirectoryTO}> the documents list
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    List<DirectoryTO> uploadDebitsDocuments(String cnpj, MultipartFile[] files) throws UnprocessableEntityException;

    /**
     * Get all bank documents of company by cnpj.
     *
     * @param cnpj   {@link String} the cnpj
     * @param userId {@link Long} the user id
     * @return {@link List}<{@link CompanyBankDocumentTO}> the company bank documents
     * @throws ResourceNotFoundException if error occurs
     */
    List<CompanyBankDocumentTO> getBankDocuments(String cnpj, long userId) throws ResourceNotFoundException;

    /**
     * Upload a file correspond to the given cnpj to the file storage
     *
     * @param cnpj   {@link String} the cnpj of the company
     * @param userId {@link Long} the user id
     * @param id     {@link Long} the bank nomenclature id
     * @param files  {@link MultipartFile}[] the array of the files to be register in the file store
     * @return <{@link CompanyBankDocumentTO}>
     * @throws UnprocessableEntityException if another error occurs independent of {@link ResourceNotFoundException}
     */
    CompanyBankDocumentTO uploadBankDocuments(String cnpj, long userId, long id, MultipartFile[] files) throws UnprocessableEntityException;

}
