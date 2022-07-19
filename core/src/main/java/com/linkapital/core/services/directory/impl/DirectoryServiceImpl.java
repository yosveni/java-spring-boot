package com.linkapital.core.services.directory.impl;

import com.linkapital.core.exceptions.StorageException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.comment.datasource.domain.Comment;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.directory.contract.enums.Type;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.storage.StorageService;
import com.linkapital.jucesp.bots.exceptions.CannotGetJucespFileException;
import com.linkapital.jucesp.bots.jucesp.DocumentMetadata;
import com.linkapital.jucesp.bots.jucesp.JucespBot;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.directory.contract.DirectoryBinder.DIRECTORY_BINDER;
import static com.linkapital.core.services.directory.contract.enums.Type.ARCHIVED_DOCUMENTS;
import static com.linkapital.core.services.directory.contract.enums.Type.COMMENT;
import static com.linkapital.core.services.directory.contract.enums.Type.REGISTRATION_FORM;
import static com.linkapital.core.services.directory.contract.enums.Type.SIMPLIFIED_CERTIFICATION;
import static com.linkapital.core.util.functions.ThrowingFunction.unchecked;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static org.springframework.util.StringUtils.replace;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    private final Logger log = LoggerFactory.getLogger(DirectoryServiceImpl.class);
    private final StorageService storageService;
    private final ResourceLoader resourceLoader;
    private final JucespBot jucespBot;

    public DirectoryServiceImpl(StorageService storageService,
                                ResourceLoader resourceLoader,
                                JucespBot jucespBot) {
        this.storageService = storageService;
        this.resourceLoader = resourceLoader;
        this.jucespBot = jucespBot;
    }

    @Override
    public Directory uploadFile(String id, MultipartFile file, Type type) throws UnprocessableEntityException {
        try {
            var ext = storageService.getExtension(file);
            var url = DIRECTORY_BINDER.bindUrl(id, ext);
            storageService.store(file.getBytes(), url);

            return DIRECTORY_BINDER.bind(type, file.getOriginalFilename(), ext, url);
        } catch (StorageException | IOException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }

    @Override
    public Directory uploadFile(String id, byte[] bytes, Type type, String fileName)
            throws UnprocessableEntityException {
        try {
            var ext = "pdf";
            var url = DIRECTORY_BINDER.bindUrl(id, ext);
            storageService.store(bytes, url);

            return DIRECTORY_BINDER.bind(type, fileName, ext, url);
        } catch (StorageException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }

    @Override
    public List<Directory> uploadFiles(String id, MultipartFile @NonNull [] files, Type type)
            throws UnprocessableEntityException {
        var directories = new ArrayList<Directory>();

        for (MultipartFile file : files)
            directories.add(uploadFile(id, file, type));

        return directories;
    }

    @Override
    public List<Directory> uploadDebitsDocuments(CompanyUser companyUser, MultipartFile[] files, Type type) {
        return stream(files)
                .map(
                        unchecked(file -> {
                                    var cnpj = companyUser.getCompany().getMainInfo().getCnpj();
                                    var directory = uploadFile(cnpj, file, type);
                                    directory.setCompanyDebits(companyUser);

                                    return directory;
                                }
                        ))
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<Directory> uploadDocuments(String cnpj, MultipartFile[] files, Type type) {
        return stream(files)
                .map(file -> {
                            try {
                                return uploadFile(cnpj, file, type);
                            } catch (UnprocessableEntityException e) {
                                log.error(e.getMessage());
                                return null;
                            }
                        }
                )
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<Directory> updloadNfeDuplicates(CompanyUser companyUser, MultipartFile[] files, Type type) {
        return stream(files)
                .map(file -> {
                            try {
                                var cnpj = companyUser.getCompany().getMainInfo().getCnpj();
                                var directory = uploadFile(cnpj, file, type);
                                directory.setCompanyNfeDuplicity(companyUser);

                                return directory;
                            } catch (UnprocessableEntityException e) {
                                log.error(e.getMessage());
                                return null;
                            }
                        }
                )
                .filter(Objects::nonNull)
                .toList();
    }


    @Override
    public List<Directory> uploadIrpfDocuments(Person person, MultipartFile[] files, Type type) {
        return stream(files)
                .map(file -> {
                            try {
                                var directory = uploadFile(person.getCpf(), file, type);
                                switch (type) {
                                    case IRPF -> directory.setPersonIrpf(person);
                                    case IRPF_RECEIPT -> directory.setPersonIrpfReceipt(person);
                                    default -> {
                                    }
                                }

                                return directory;
                            } catch (UnprocessableEntityException e) {
                                log.error(e.getMessage());
                                return null;
                            }
                        }
                )
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public byte[] loadFile(String url) throws UnprocessableEntityException {
        try {
            return storageService.getFile(url);
        } catch (StorageException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }

    @Override
    public void delete(String url) {
        storageService.deleteFile(url);
    }

    @Override
    public void deleteFiles(@NonNull Collection<Directory> directories) {
        directories.forEach(directory -> delete(directory.getUrl()));
    }

    @Override
    public void uploadJucespDocuments(String fileName,
                                      Type type,
                                      InputStream stream,
                                      Company company) throws UnprocessableEntityException {
        if (stream == null)
            return;

        var cnpj = company.getMainInfo().getCnpj();
        var url = format("%s/%s.pdf", cnpj, UUID.randomUUID());

        sendToStorage(stream, url);
        DIRECTORY_BINDER.bind(url, fileName, company, type);
    }

    @Override
    public void uploadJucespArchivedDocuments(@NonNull List<DocumentMetadata> archivedDocuments,
                                              Company company) throws UnprocessableEntityException {
        if (archivedDocuments.isEmpty())
            return;

        var cnpj = company.getMainInfo().getCnpj();
        for (DocumentMetadata documentMetadata : archivedDocuments) {
            var url = format("%s/%s.pdf", cnpj, UUID.randomUUID());
            var date = replace(documentMetadata.getDate(), "/", "-");

            sendToStorage(documentMetadata.getData(), url);
            DIRECTORY_BINDER.bind(url, format("%s %s", documentMetadata.getDescription(), date), company,
                    ARCHIVED_DOCUMENTS);
        }
    }

    @Override
    public void uploadCommentFiles(MultipartFile[] files, @NonNull Comment comment) {
        var directories = stream(files)
                .map(file -> {
                    try {
                        return uploadFile(String.valueOf(comment.getId()), file, COMMENT);
                    } catch (UnprocessableEntityException e) {
                        log.error(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        comment.getAttachments().addAll(directories);
    }

    @Override
    public void uploadJucepDocumentBot(String fileName, Type type, @NonNull Company company) {
        var mainInfo = company.getMainInfo();
        InputStream inputStream = null;

        try {
            inputStream = type == SIMPLIFIED_CERTIFICATION
                    ? jucespBot.getSimplifiedCertification(mainInfo.getSocialReason())
                    : jucespBot.getRegistrationForm(mainInfo.getSocialReason());
        } catch (CannotGetJucespFileException e) {
            log.error(e.getMessage());
        }

        try {
            uploadJucespDocuments(fileName, type, inputStream, company);
        } catch (UnprocessableEntityException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void uploadJucepArchivedDocumentsBot(@NonNull Company company) {
        var mainInfo = company.getMainInfo();
        List<DocumentMetadata> result;

        try {
            result = jucespBot.getArchivedDocuments(mainInfo.getSocialReason());
        } catch (CannotGetJucespFileException ignored) {
            result = List.of();
        }

        try {
            uploadJucespArchivedDocuments(result, company);
        } catch (UnprocessableEntityException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void uploadJucespDocumentsLocal(@NonNull Company company) throws UnprocessableEntityException {
        var fccInputStream = findDocumentLocal(REGISTRATION_FORM, 0);
        var csInputStream = findDocumentLocal(SIMPLIFIED_CERTIFICATION, 0);

        uploadJucespDocuments(msg("directory.complete.registration.form"), REGISTRATION_FORM, fccInputStream,
                company);
        uploadJucespDocuments(msg("directory.simplified.certificate"), SIMPLIFIED_CERTIFICATION, csInputStream,
                company);

        var daInputStreams = new ArrayList<InputStream>();
        var present = new AtomicBoolean(true);
        var doc = 1;

        while (present.get()) {
            Optional
                    .ofNullable(findDocumentLocal(ARCHIVED_DOCUMENTS, doc++))
                    .ifPresentOrElse(daInputStreams::add, () -> present.set(false));
        }

        doc = 1;
        for (InputStream inputStream : daInputStreams)
            uploadJucespDocuments(msg("directory.file.archived", doc++), ARCHIVED_DOCUMENTS, inputStream,
                    company);
    }

    //region Find the document in the project locale
    private InputStream findDocumentLocal(Type type, int x) {
        return Optional.of(resourceLoader.getResource(getRuta(type, x)))
                .map(resource -> {
                    try {
                        return resource.getInputStream();
                    } catch (IOException e) {
                        return null;
                    }
                })
                .orElse(null);
    }
    //endregion

    //region Returns the local path for the jucep documents
    private String getRuta(Type type, int x) {
        var path = "classpath:mock_documents/";

        return switch (type) {
            case REGISTRATION_FORM -> path + "fcc.pdf";
            case SIMPLIFIED_CERTIFICATION -> path + "cs.pdf";
            default -> path + format("da%s.pdf", x);
        };
    }
    //endregion

    //region Send an stream file to the storage
    private void sendToStorage(InputStream stream, String url) throws UnprocessableEntityException {
        storageService.storeJucesp(storageService.getFile(stream), url);
    }
    //endregion

}
