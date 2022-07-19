package com.linkapital.core.services.directory;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.comment.datasource.domain.Comment;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.contract.enums.Type;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.jucesp.bots.jucesp.DocumentMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default interface for {@link DirectoryService}
 * Has the responsibility of sending files corresponding to a cnpj to the application's file storage
 *
 * @since 0.0.1
 */
public interface DirectoryService {

    /**
     * Send a file to the file storage
     *
     * @param id   {@link String} the company cnpj
     * @param file {@link MultipartFile} a file to be load to the file storage
     * @param type {@link Type} used to create the type of directory in the file store
     * @return {@link Directory}
     * @throws UnprocessableEntityException if any error occur sending the file to the file storage
     */
    Directory uploadFile(String id, MultipartFile file, Type type) throws UnprocessableEntityException;

    /**
     * Send a file to the file storage
     *
     * @param id       {@link String} the company cnpj
     * @param bytes    {@link byte[]} a array bytes to be load to the file storage
     * @param type     {@link Type} used to create the type of directory in the file store
     * @param fileName {@link String} the file name of document
     * @return {@link Directory}
     * @throws UnprocessableEntityException if any error occur sending the file to the file storage
     */
    Directory uploadFile(String id, byte[] bytes, Type type, String fileName) throws UnprocessableEntityException;

    /**
     * Send a files to the file storage
     *
     * @param id    {@link String} the company cnpj
     * @param files {@link MultipartFile}[] a files to be load to the file storage
     * @param type  {@link Type} used to create the type of directory in the file store
     * @return {@link List}<{@link Directory}>
     * @throws UnprocessableEntityException if any error occur sending the file to the file storage
     */
    List<Directory> uploadFiles(String id, MultipartFile[] files, Type type) throws UnprocessableEntityException;

    /**
     * Upload the OPEN_DEBTS documents for a company
     *
     * @param companyUser {@link CompanyUser} the company entity
     * @param files       {@link MultipartFile}[] to be register in the file store
     * @param type        {@link Type} Type of document to register in the file store
     * @return {@link List}<{@link Directory}> a list of Directory
     */
    List<Directory> uploadDebitsDocuments(CompanyUser companyUser, MultipartFile[] files, Type type);

    /**
     * Upload the documents for a company
     *
     * @param cnpj  {@link String} the company entity
     * @param files {@link MultipartFile}[] to be register in the file store
     * @param type  {@link Type} Type of document to register in the file store
     * @return {@link List}<{@link Directory}> a list of Directory
     */
    List<Directory> uploadDocuments(String cnpj, MultipartFile[] files, Type type);

    /**
     * * Upload Nfe Duplicates
     *
     * @param companyUser {@link CompanyUser} the company user
     * @param files       {@link MultipartFile}[] the duplicates of fiscal notes
     * @param type        {@link Type} Type of document to register in the file store
     * @return {@link List}<{@link Directory}> a list of Directory
     */
    List<Directory> updloadNfeDuplicates(CompanyUser companyUser, MultipartFile[] files, Type type);

    /**
     * Upload the IRPF documents for a person
     *
     * @param person {@link Person} the person entity
     * @param files  {@link ArrayList} an array of {@link MultipartFile} to be register in the file store
     * @param type   {@link Type} Type of document to register in the file store
     * @return {@link Directory} a list of Directory
     */
    List<Directory> uploadIrpfDocuments(Person person, MultipartFile[] files, Type type);

    /**
     * Get a company file from the file storage
     *
     * @param url {@link String}
     * @return an array of byte form the retrieved file
     * @throws UnprocessableEntityException if any error occur retrieving the data from the given url
     */
    byte[] loadFile(String url) throws UnprocessableEntityException;

    /**
     * Upload documents for the given company if the current profile is not production or company social reason
     * don't has text then return the company without any updated else retrieve from the bot the Registration form,
     * simplified certification and the archived documents by the company social reason
     *
     * @param company {@link Company} the company that has the data to retrieve documents from file storage
     * @throws UnprocessableEntityException if any error occur retrieving data from the file storage
     */
    void uploadJucespDocumentsLocal(Company company) throws UnprocessableEntityException;

    /**
     * Upload the Jucesp Documents
     *
     * @param fileName {@link String} the file name
     * @param type     {@link Type} the file type
     * @param stream   {@link InputStream} the InputStream file
     * @param company  {@link Company} the company to upload the document
     * @throws UnprocessableEntityException if an error occurs while the files are being processed
     */
    void uploadJucespDocuments(String fileName, Type type, InputStream stream, Company company) throws UnprocessableEntityException;

    /**
     * Upload the Jucesp Documents
     *
     * @param archivedDocuments {@link List<DocumentMetadata>} the List<DocumentMetadata>
     * @param company           {@link Company} the company to upload the document
     * @throws UnprocessableEntityException if an error occurs while the files are being processed
     */
    void uploadJucespArchivedDocuments(List<DocumentMetadata> archivedDocuments, Company company) throws UnprocessableEntityException;

    /**
     * Execute a call to the boot in a separate application thread
     *
     * @param fileName {@link String} the file name
     * @param type     {@link Type} the file type
     * @param company  {@link Company} the company to upload the document
     */
    void uploadJucepDocumentBot(String fileName, Type type, Company company);

    /**
     * Execute a call to the boot in a separate application thread
     *
     * @param company {@link Company} the company to upload the document
     */
    void uploadJucepArchivedDocumentsBot(Company company);

    /**
     * Delete a file form store by url
     *
     * @param url {@link String}
     */
    void delete(String url);

    /**
     * Delete a files form store
     *
     * @param directories {@link Collection}<{@link Directory}> the directories to delete
     */
    void deleteFiles(Collection<Directory> directories);

    /**
     * Add directories to a comment
     * Send to file storage each file in the collection and receive
     *
     * @param files           {@link MultipartFile} Array of multipart files requested by the resource controller
     * @param commentDatabase The Comment getting from datasource in {@code uploadFile(Long, MultipartFile[])} method
     */
    void uploadCommentFiles(MultipartFile[] files, Comment commentDatabase);

}
