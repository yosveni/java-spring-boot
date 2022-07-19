package com.linkapital.core.services.company_user.impl;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_nomenclature.BankNomenclatureService;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankDocumentTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.update.UpdateBankDocumentTO;
import com.linkapital.core.services.bank_nomenclature.datasource.CompanyBankDocumentRepository;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.CompanyBankDocument;
import com.linkapital.core.services.company.CompanyExcelService;
import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.contract.to.CompanyTO;
import com.linkapital.core.services.company.contract.to.DocumentCompanyTO;
import com.linkapital.core.services.company.contract.to.LearningSessionConfirmTO;
import com.linkapital.core.services.company.contract.to.SessionConfirmedTO;
import com.linkapital.core.services.company.datasource.domain.LearningAnalysis;
import com.linkapital.core.services.company_user.CompanyUserService;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.CompanyTransferTO;
import com.linkapital.core.services.company_user.contract.to.LightBackOfficeTO;
import com.linkapital.core.services.company_user.datasource.CompanyUserRepository;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.invoice.InvoiceService;
import com.linkapital.core.services.method_k.ScoreAnalysisService;
import com.linkapital.core.services.method_k.contract.to.ScoreSummaryTO;
import com.linkapital.core.services.notification.WebsocketService;
import com.linkapital.core.services.security.UserService;
import com.linkapital.core.services.security.datasource.domain.User;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.bank_nomenclature.contract.BankNomenclatureBinder.BANK_NOMENCLATURE_BINDER;
import static com.linkapital.core.services.bank_nomenclature.contract.BankNomenclatureBinder.buildCompanyBankDocumentNotification;
import static com.linkapital.core.services.bank_nomenclature.contract.BankNomenclatureBinder.cloneList;
import static com.linkapital.core.services.bank_nomenclature.contract.enums.CompanyBankDocumentState.ANALYSIS;
import static com.linkapital.core.services.bank_nomenclature.contract.enums.CompanyBankDocumentState.APPROVED;
import static com.linkapital.core.services.bank_nomenclature.contract.enums.CompanyBankDocumentState.REJECTED;
import static com.linkapital.core.services.company.contract.CompanyBinder.COMPANY_BINDER;
import static com.linkapital.core.services.company.contract.CompanyBinder.bindToSessionConfirmedTO;
import static com.linkapital.core.services.company.contract.CompanyBinder.buildDocumentCompanyTO;
import static com.linkapital.core.services.company.contract.CompanyBinder.buildLearningAnalysis;
import static com.linkapital.core.services.company.contract.CompanyBinder.buildLightBackOfficeTO;
import static com.linkapital.core.services.company.contract.CompanyBinder.getCnpjListOfUser;
import static com.linkapital.core.services.company.contract.CompanyBinder.set;
import static com.linkapital.core.services.company.contract.CompanyBinder.sortListCompanyClient;
import static com.linkapital.core.services.company.validator.CompanyValidator.validateListContaints;
import static com.linkapital.core.services.company.validator.CompanyValidator.validateListEmpty;
import static com.linkapital.core.services.company_user.validator.CompanyUserValidator.validateLength;
import static com.linkapital.core.services.directory.contract.DirectoryBinder.DIRECTORY_BINDER;
import static com.linkapital.core.services.directory.contract.enums.Type.BANK;
import static com.linkapital.core.services.directory.contract.enums.Type.OPEN_DEBTS;
import static com.linkapital.core.services.method_k.contract.ScoreSummaryBinder.SCORE_SUMMARY_BINDER;
import static com.linkapital.core.services.method_k.contract.enums.ScoreType.ALL;
import static com.linkapital.core.services.notification.WebsocketService.BANK_DOCUMENT_STATUS_CHANGED;
import static com.linkapital.core.services.notification.contract.enums.WebsocketBroker.TOPIC;
import static com.linkapital.core.services.security.contract.enums.Authority.ENTREPRENEUR;
import static com.linkapital.core.services.security.contract.enums.Authority.PARTNER;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;

@Service
@Transactional
public class CompanyUserServiceImpl implements CompanyUserService {

    private final CompanyUserRepository companyUserRepository;
    private final CompanyBankDocumentRepository companyBankDocumentRepository;
    private final CompanyService companyService;
    private final DirectoryService directoryService;
    private final BankNomenclatureService bankNomenclatureService;
    private final InvoiceService invoiceService;
    private final CompanyExcelService companyExcelService;
    private final UserService userService;
    private final ScoreAnalysisService scoreAnalysisService;
    private final WebsocketService webSocketService;

    public CompanyUserServiceImpl(CompanyUserRepository companyUserRepository,
                                  CompanyBankDocumentRepository companyBankDocumentRepository,
                                  CompanyService companyService,
                                  DirectoryService directoryService,
                                  BankNomenclatureService bankNomenclatureService,
                                  InvoiceService invoiceService,
                                  CompanyExcelService companyExcelService,
                                  UserService userService,
                                  ScoreAnalysisService scoreAnalysisService,
                                  WebsocketService webSocketService) {
        this.companyUserRepository = companyUserRepository;
        this.companyBankDocumentRepository = companyBankDocumentRepository;
        this.companyService = companyService;
        this.directoryService = directoryService;
        this.bankNomenclatureService = bankNomenclatureService;
        this.invoiceService = invoiceService;
        this.companyExcelService = companyExcelService;
        this.userService = userService;
        this.scoreAnalysisService = scoreAnalysisService;
        this.webSocketService = webSocketService;
    }

    @Override
    public CompanyUser save(CompanyUser companyUser) {
        return companyUserRepository.saveAndFlush(companyUser);
    }

    @Override
    public List<LightBackOfficeTO> saveCnpjsFile(MultipartFile file) throws UnprocessableEntityException {
        var cnpjList = companyExcelService.parseCnpj(file);
        validateListEmpty(cnpjList, msg("company.files.cnpj.not.found"));

        return saveCnpjList(cnpjList);
    }

    @Override
    public List<LightBackOfficeTO> saveCnpjList(@NonNull List<String> cnpjList) throws UnprocessableEntityException {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        cnpjList.removeAll(getCnpjListOfUser.apply(user));
        validateListEmpty(cnpjList, msg("company.files.cnpj.not.found"));
        cnpjList = new ArrayList<>(new HashSet<>(cnpjList));

        var companiesFound = companyService.getByCnpjIn(cnpjList);
        var companies = companyService.buildRelations(user, cnpjList, companiesFound);

        return COMPANY_BINDER.bindToLightBackOfficeListTO(companies, user);
    }

    @Override
    public SessionConfirmedTO confirmLearningSession(@NonNull LearningSessionConfirmTO to)
            throws UnprocessableEntityException {
        var companyUser = get(to.getCnpj(), null);
        var learningSession = to.getLearningSession();

        validateListContaints(companyUser.getLearningSessions(), learningSession,
                msg("company.learning.session.already.confirmed", learningSession));
        companyUser.getLearningSessions().add(learningSession);

        return bindToSessionConfirmedTO.apply(save(companyUser));
    }

    @Override
    public CompanyUser get(String cnpj, Long userId) throws UnprocessableEntityException {
        var id = userId == null
                ? ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
                : userId;
        return companyUserRepository.findByCompany_MainInfo_CnpjAndUser_Id(cnpj, id)
                .orElseThrow(() -> new UnprocessableEntityException(
                        msg("company.user.relationship.has.not.found")));
    }

    @Override
    public DocumentCompanyTO getDocumentsCompany(String cnpj, Long userId) throws UnprocessableEntityException {
        var companyUser = get(cnpj, userId);
        return buildDocumentCompanyTO.apply(companyUser);
    }

    @Override
    public List<DirectoryTO> uploadDebitsDocuments(String cnpj,
                                                   MultipartFile[] files) throws UnprocessableEntityException {
        validateLength(files);
        var companyUser = get(cnpj);
        var directories = directoryService.uploadDebitsDocuments(companyUser, files, OPEN_DEBTS);
        companyUser.getDebtDocuments().addAll(directories);

        return DIRECTORY_BINDER.bind(save(companyUser).getDebtDocuments());
    }

    @Override
    public CompanyBankDocumentTO uploadBankDocuments(String cnpj,
                                                     long userId,
                                                     long id,
                                                     MultipartFile[] files) throws UnprocessableEntityException {
        validateLength(files);
        var companyUser = get(cnpj, userId);
        var bankNomenclature = bankNomenclatureService.getById(id);
        var directories = directoryService.uploadDocuments(cnpj, files, BANK);
        var build = buildCompanyBankDocument(bankNomenclature, companyUser, directories);

        return BANK_NOMENCLATURE_BINDER.bind(build);
    }

    @Override
    public LightBackOfficeTO transferCompanyToUser(@NonNull CompanyTransferTO to) throws UnprocessableEntityException {
        var user = userService.getById(to.getUserIdToTransfer());
        var userCnpjList = getCnpjListOfUser.apply(user);

        validateListContaints(userCnpjList, to.getCnpj(), msg("company.already.exist.for.client",
                user.getEmail()));

        var companyUser = get(to.getCnpj(), to.getUserIdProperty());
        set.accept(companyUser, user);

        return buildLightBackOfficeTO.apply(save(companyUser));
    }

    @Override
    public CompanyBankDocumentTO updateBankDocumentState(@NonNull UpdateBankDocumentTO to)
            throws UnprocessableEntityException {

        var companyUser = get(to.getCnpj(), to.getUserId());
        var companyBankDocumentTO = companyUser.getBankDocuments()
                .stream()
                .filter(companyBankDocument -> companyBankDocument.getId().equals(to.getBankDocumentId()))
                .findFirst()
                .map(companyBankDocument -> BANK_NOMENCLATURE_BINDER.bind(
                        companyBankDocumentRepository.saveAndFlush(companyBankDocument
                                .withDescriptionState(to.getDescriptionState())
                                .withState(to.getState()))))
                .orElseThrow(() -> new UnprocessableEntityException(msg("bank.document.not.found",
                        to.getBankDocumentId())));

        if (to.getState().equals(APPROVED) || to.getState().equals(REJECTED)) {
            webSocketService.dispatch(
                    companyUser.getUser().getEmail(),
                    TOPIC,
                    BANK_DOCUMENT_STATUS_CHANGED,
                    msg(to.getState().equals(APPROVED)
                                    ? "bank.nomenclature.documents.approved"
                                    : "bank.nomenclature.documents.rejected",
                            companyBankDocumentTO.getBankNomenclature().getDescription()),
                    buildCompanyBankDocumentNotification.apply(to.getCnpj(), companyBankDocumentTO));
        }

        return companyBankDocumentTO;
    }

    @Override
    public CompanyTO getRelation(String cnpj, Long userId) throws ResourceNotFoundException {
        try {
            var companyUser = get(cnpj, userId);
            var to = COMPANY_BINDER.buildCompanyTO(companyUser);

            if (companyUser.isInitIndicativeOfferThree() && to.getCompanyLearning3() == null) {
                if (companyUser.getLearningAnalysis() == null)
                    companyUser.setLearningAnalysis(new LearningAnalysis());

                if (companyUser.getLearningAnalysis().getLearningThree() == null)
                    to.setCompanyLearning3(invoiceService.buildAnalysis(companyUser));

                save(buildLearningAnalysis.apply(companyUser, to.getCompanyLearning3(), 3));
            }

            return to;
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public ScoreSummaryTO getScoreAnalysis(String cnpj, Long userId) throws ResourceNotFoundException {
        CompanyUser companyUser;
        try {
            companyUser = get(cnpj, userId);
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

        if (companyUser.getScoreAnalysis().isEmpty()) {
            scoreAnalysisService.updateResumeScore(companyUser, ALL);
            companyUser = save(companyUser);
        }

        return SCORE_SUMMARY_BINDER.bind(companyUser);
    }

    @Override
    public SessionConfirmedTO getSessionConfirmed(String cnpj) throws ResourceNotFoundException {
        CompanyUser companyUser;
        try {
            companyUser = get(cnpj);
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

        return bindToSessionConfirmedTO.apply(companyUser);
    }

    @Override
    public CompanyClientTO getCompanyClient(String cnpj) throws ResourceNotFoundException {
        try {
            var companyUser = get(cnpj);
            var nomenclatures = bankNomenclatureService.getAllByPartnerBankLinkapital();

            return populateBankNomenclatures(companyUser, nomenclatures);
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<CompanyClientTO> getAllByUserClient() throws UnprocessableEntityException {
        return populateBankNomenclatures(getAuthenticateUser().getCompanies());
    }

    @Override
    public List<CompanyBankDocumentTO> getBankDocuments(String cnpj, long userId) throws ResourceNotFoundException {
        CompanyUser companyUser;
        try {
            companyUser = get(cnpj, userId);
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

        return companyUser.getBankDocuments().isEmpty()
                ? emptyList()
                : COMPANY_BINDER.bindToCompanyBankDocumentListTO(companyUser.getBankDocuments());
    }

    //region The list of bank nomenclators is populated to the list of companies
    private List<CompanyClientTO> populateBankNomenclatures(@NonNull Set<CompanyUser> companies) {
        if (companies.isEmpty())
            return emptyList();

        var bankNomenclatures = bankNomenclatureService.getAllByPartnerBankLinkapital();
        var companiesClients = companies
                .stream()
                .map(companyUser -> populateBankNomenclatures(companyUser, bankNomenclatures))
                .toList();

        return sortListCompanyClient.apply(companiesClients);
    }
    //endregion

    //region The list of bank nomenclators is populated to the company
    private CompanyClientTO populateBankNomenclatures(@NonNull CompanyUser companyUser,
                                                      List<CompanyBankNomenclatureTO> bankNomenclatures) {
        var user = companyUser.getUser();
        var to = COMPANY_BINDER.bindToClientTO(companyUser);
        var authority = user.getRole().getAuthority();

        if (authority.equals(PARTNER) || authority.equals(ENTREPRENEUR)) {
            var cloned = cloneList.apply(bankNomenclatures);
            var nomenclatures = bankNomenclatureService.getAllByCompanyUser(companyUser,
                    cloned);

            return to.withBankNomenclature(nomenclatures);
        } else
            return to;
    }
    //endregion

    //region Get the user authenticate
    private User getAuthenticateUser() throws UnprocessableEntityException {
        return userService.getById(
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }
    //endregion

    //region Returns or builds a Company Bank Document with the uploaded documents
    private CompanyBankDocument buildCompanyBankDocument(BankNomenclature bankNomenclature,
                                                         @NonNull CompanyUser companyUser,
                                                         List<Directory> directories) {
        var atomic = new AtomicReference<CompanyBankDocument>();
        var bankDocuments = companyUser.getBankDocuments();
        bankDocuments
                .stream()
                .filter(bankDocument -> bankDocument.getBankNomenclature().getId().equals(bankNomenclature.getId()))
                .findFirst()
                .ifPresentOrElse(bankDocument -> {
                    directoryService.deleteFiles(bankDocument.getDirectories());
                    bankDocument.getDirectories().clear();
                    bankDocument.getDirectories().addAll(directories);
                    bankDocument.setDescriptionState(null);
                    bankDocument.setState(ANALYSIS);
                    atomic.set(bankDocument);
                }, () -> {
                    var companyBankDocument = new CompanyBankDocument()
                            .withBankNomenclature(bankNomenclature)
                            .withDirectories(directories);
                    bankDocuments.add(companyBankDocument);
                    atomic.set(companyBankDocument);
                });

        var bankDocumentsUpdated = save(companyUser).getBankDocuments()
                .stream()
                .sorted(comparing(CompanyBankDocument::getId))
                .toList();

        return bankDocumentsUpdated
                .stream()
                .filter(companyBankDocument -> companyBankDocument.getId().equals(atomic.get().getId()))
                .findFirst()
                .orElseGet(() -> bankDocumentsUpdated.get(bankDocumentsUpdated.size() - 1));
    }
    //endregion

}
