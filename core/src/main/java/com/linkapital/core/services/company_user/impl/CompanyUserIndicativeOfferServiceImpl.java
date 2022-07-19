package com.linkapital.core.services.company_user.impl;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService;
import com.linkapital.core.services.company_user.CompanyUserService;
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
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.indicative_offer.impl.FacadeIndicativeOfferServiceImpl;
import com.linkapital.core.services.invoice.InvoiceService;
import com.linkapital.core.services.method_k.ScoreAnalysisService;
import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.security.UserService;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.services.sped.SpedFileService;
import com.linkapital.core.services.sped.SpedService;
import com.linkapital.core.services.sped.contract.to.CreateSpedTO;
import com.linkapital.core.services.sped.datasource.domain.Sped;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.company.contract.CompanyBinder.COMPANY_BINDER;
import static com.linkapital.core.services.company.contract.CompanyBinder.bindToDataInitLearning2TO;
import static com.linkapital.core.services.company.contract.CompanyBinder.bindToLearning2TO;
import static com.linkapital.core.services.company.contract.CompanyBinder.buildLearning2TO;
import static com.linkapital.core.services.company.contract.CompanyBinder.buildLearning3TO;
import static com.linkapital.core.services.company.contract.CompanyBinder.buildLearning4TO;
import static com.linkapital.core.services.company.contract.CompanyBinder.buildLearningAnalysis;
import static com.linkapital.core.services.company.contract.CompanyBinder.getPartners;
import static com.linkapital.core.services.company.validator.CompanyValidator.validateListEmpty;
import static com.linkapital.core.services.company_user.validator.CompanyUserValidator.validatePropertyGuaranteeDocumentExist;
import static com.linkapital.core.services.directory.contract.enums.Type.BALANCETE;
import static com.linkapital.core.services.directory.contract.enums.Type.NFE_DUPLICITY;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.SELECTED;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.WITHOUT_OFFER;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.WITH_OFFER;
import static com.linkapital.core.services.method_k.contract.enums.ScoreType.ALL;
import static com.linkapital.core.services.method_k.contract.enums.ScoreType.HABITUALITY;
import static com.linkapital.core.services.method_k.contract.enums.ScoreType.STRENGTH;
import static com.linkapital.core.services.security.contract.enums.Authority.BACKOFFICE;
import static com.linkapital.core.services.security.contract.enums.Authority.SECURITY;
import static com.linkapital.core.services.sped.contract.SpedBinder.bindToCompanySped;
import static com.linkapital.core.services.sped.validator.SpedFileValidator.validateSpedFile;

@Service
@Transactional
public class CompanyUserIndicativeOfferServiceImpl implements CompanyUserIndicativeOfferService {

    private final CompanyService companyService;
    private final CompanyUserService companyUserService;
    private final FacadeIndicativeOfferServiceImpl indicativeOfferService;
    private final InvoiceService invoiceService;
    private final SpedService spedService;
    private final SpedFileService spedFileService;
    private final DirectoryService directoryService;
    private final UserService userService;
    private final PersonService personService;
    private final ScoreAnalysisService scoreAnalysisService;

    public CompanyUserIndicativeOfferServiceImpl(CompanyService companyService,
                                                 CompanyUserService companyUserService,
                                                 FacadeIndicativeOfferServiceImpl indicativeOfferService,
                                                 InvoiceService invoiceService,
                                                 SpedService spedService,
                                                 SpedFileService spedFileService,
                                                 DirectoryService directoryService,
                                                 UserService userService,
                                                 PersonService personService,
                                                 ScoreAnalysisService scoreAnalysisService) {
        this.companyService = companyService;
        this.companyUserService = companyUserService;
        this.indicativeOfferService = indicativeOfferService;
        this.invoiceService = invoiceService;
        this.spedService = spedService;
        this.spedFileService = spedFileService;
        this.directoryService = directoryService;
        this.userService = userService;
        this.personService = personService;
        this.scoreAnalysisService = scoreAnalysisService;
    }

    @Override
    public CompanyLearning1TO initLearningOne(@NonNull InitLearningOneTO to) throws UnprocessableEntityException {
        var user = userService.getById(getUserId(to.getUserId()));
        var companyUser = companyService.processInitLearningOne(user, to);
        var partners = getPartners.apply(companyUser.getCompany());
        personService.getPersonPartnerFromApi(partners);
        setLearningOne(to, companyUser);
        scoreAnalysisService.updateResumeScore(companyUser, ALL);

        return COMPANY_BINDER.buildLearning1TO(companyUserService.save(companyUser));
    }

    @Override
    public CompanyLearning1TO updateLearningOne(@NonNull InitLearningOneTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), getUserId(to.getUserId()));
        setLearningOne(to, companyUser);
        scoreAnalysisService.updateResumeScore(companyUser, ALL);

        return COMPANY_BINDER.buildLearning1TO(companyUserService.save(companyUser));
    }

    @Override
    public CompanyLearning2TO initLearningTwo(@NonNull InitLearningTwoTO to) throws UnprocessableEntityException,
            ResourceNotFoundException {
        var companyUser = companyUserService.get(to.getCnpj(), getUserId(to.getUserId()));
        validateListEmpty(to.getPartnersSpouse(), msg("company.partners.list.empty"));

        var allPartners = companyService.getAllPartners(to.getCnpj());
        var personPartners = personService.getPersonPartnerData(allPartners, to.getPartnersSpouse());
        validateListEmpty(personPartners, msg("company.partners.not.found"));

        toOfferSelectedByUserRole(companyUser, 2);

        var to2 = bindToLearning2TO.apply(companyUser, personPartners, allPartners);
        initLearningThreeAndFour(companyUser, to.getAvgReceiptTerm(), to.getInvoicesFile(), null,
                to.getSpedFiles(), null);

        companyUserService.save(buildLearningAnalysis.apply(companyUser.withInitIndicativeOfferTwo(true), to2, 2));

        return to2;
    }

    @Override
    public CompanyLearning2TO updateLearningTwo(InitLearningTwoTO to) throws UnprocessableEntityException,
            ResourceNotFoundException {
        return initLearningTwo(to);
    }

    @Override
    public CompanyLearning3TO initLearningThree(@NonNull InitLearningThreeTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), getUserId(to.getUserId()));
        var to3 = processLearningThree(true, companyUser
                .withAvgReceiptTermInvoices(to.getAvgReceiptTerm()), to.getInvoiceFiles());

        toOfferSelectedByUserRole(companyUser, 3);
        initLearningThreeAndFour(companyUser, null, null, to.getSpedBalanceFile(),
                to.getSpedFiles(), to.getNfeDuplicatesFiles());
        companyUserService.save(companyUser.withInitIndicativeOfferThree(true));

        return to3;
    }

    @Override
    public CompanyLearning3TO updateLearningThree(@NonNull UpdateLearningThreeTO to)
            throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), getUserId(to.getUserId()));
        var to3 = processLearningThree(to.isClean(), companyUser
                .withAvgReceiptTermInvoices(to.getAvgReceiptTerm()), to.getInvoiceFiles());

        toOfferSelectedByUserRole(companyUser, 3);
        initLearningThreeAndFour(companyUser, null, null, to.getSpedBalanceFile(),
                to.getSpedFiles(), to.getNfeDuplicatesFiles());
        companyUserService.save(companyUser);

        return to3;
    }

    @Override
    public CompanySpedTO initLearningFour(@NonNull InitLearningFourTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), getUserId(to.getUserId()));
        var to4 = processLearningFour(to.getCnpj(), true, companyUser, to.getSpedFiles());

        toOfferSelectedByUserRole(companyUser, 4);
        initLearningThreeAndFour(companyUser, to.getAvgReceiptTerm(), to.getInvoicesFile(), to.getSpedBalanceFile(),
                null, to.getNfeDuplicatesFiles());
        companyUserService.save(companyUser.withInitIndicativeOfferFour(true));

        return to4;
    }

    @Override
    public CompanySpedTO updateLearningFour(@NonNull UpdateLearningFourTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), getUserId(to.getUserId()));
        var to4 = processLearningFour(to.getCnpj(), to.isClean(), companyUser, to.getSpedFiles());

        toOfferSelectedByUserRole(companyUser, 4);
        initLearningThreeAndFour(companyUser, to.getAvgReceiptTerm(), to.getInvoicesFile(),
                to.getSpedBalanceFile(), null, to.getNfeDuplicatesFiles());
        companyUserService.save(companyUser);

        return to4;
    }

    @Override
    public CompanyLearning4TO uploadTxtSped(String cnpj,
                                            Long userId,
                                            MultipartFile[] files) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(cnpj, getUserId(userId));
        var speds = spedService.readSpedFiles(cnpj, files);

        for (Sped sped : speds)
            validateSpedFile(companyUser.getSpeds(), sped);

        return processIndicativeOfferFourSpedFile(companyUser.withSpeds(speds, false));
    }

    @Override
    public CompanyLearning4TO uploadExcelSped(String cnpj,
                                              Long userId,
                                              MultipartFile file) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(cnpj, getUserId(userId));
        var sped = spedFileService.parseSped(companyUser.getSpeds(), file);
        validateSpedFile(companyUser.getSpeds(), sped);
        companyUser.getSpeds().add(sped);

        return processIndicativeOfferFourSpedFile(companyUser);
    }

    @Override
    public CompanyLearning4TO analysisLearningFour(@NonNull CreateSpedTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), getUserId(to.getUserId()));
        spedService.updateSpedList(companyUser, to);

        var to4 = spedService.getAnalysis(companyUser);
        companyUser = companyUserService.save(buildLearningAnalysis.apply(companyUser, to4, 4));

        scoreAnalysisService.updateResumeScore(companyUser, STRENGTH, HABITUALITY);

        return to4;
    }

    @Override
    public CompanyClientTO confirmLearningOne(@NonNull CompanyConfirm1TO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), to.getUserId());
        companyUser = companyUserService.save(confirmLearningOffer(companyUser, 1));

        return COMPANY_BINDER.bindToClientTO(companyUser);
    }

    @Override
    public CompanyClientTO confirmLearningTwo(@NonNull LearningConfirmTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), to.getUserId());
        companyUser = companyUserService.save(confirmLearningOffer(companyUser, 2));

        return COMPANY_BINDER.bindToClientTO(companyUser);
    }

    @Override
    public CompanyClientTO confirmLearningThree(@NonNull LearningConfirmTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), to.getUserId());
        companyUser = companyUserService.save(confirmLearningOffer(companyUser, 3));

        return COMPANY_BINDER.bindToClientTO(companyUser);
    }

    @Override
    public CompanyClientTO confirmLearningFour(@NonNull LearningConfirmTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), to.getUserId());
        companyUser = companyUserService.save(confirmLearningOffer(companyUser, 4));

        return COMPANY_BINDER.bindToClientTO(companyUser);
    }

    @Override
    public DataInitLearning2TO getInitLearningTwo(String cnpj) throws ResourceNotFoundException,
            UnprocessableEntityException {
        var companyUser = companyUserService.get(cnpj);
        var partners = companyService.getAllPartners(cnpj);

        return bindToDataInitLearning2TO.apply(companyUser, partners);
    }

    @Override
    public GenericTO getIndicativeOfferAnalysis(String cnpj,
                                                Long userId,
                                                int indicativeOfferType) throws ResourceNotFoundException {
        CompanyUser companyUser;
        try {
            companyUser = companyUserService.get(cnpj, userId);
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

        return switch (indicativeOfferType) {
            case 1 -> COMPANY_BINDER.buildLearningTO(companyUser);
            case 2 -> buildLearning2TO.apply(companyUser);
            case 3 -> buildLearning3TO.apply(companyUser);
            default -> buildLearning4TO.apply(companyUser);
        };
    }

    //region Operations for the initialization or updating of the indicative 1 offer
    private void setLearningOne(@NonNull InitLearningOneTO to,
                                @NonNull CompanyUser companyUser) throws UnprocessableEntityException {
        toOfferSelectedByUserRole(companyUser
                .withInitIndicativeOfferOne(true)
                .withCreditRequested(to.getCreditRequested())
                .withInvoicingInformed(to.getInvoicingInformed()), 1);
        initLearningThreeAndFour(companyUser, to.getAvgReceiptTerm(), to.getInvoicesFile(), to.getSpedBalanceFile(),
                to.getSpedFiles(), to.getNfeDuplicatesFiles());
    }
    //endregion

    //region Operations for the initialization or updating of the indicative 3 offer
    private CompanyLearning3TO processLearningThree(boolean clean,
                                                    CompanyUser companyUser,
                                                    MultipartFile invoicesFile) throws UnprocessableEntityException {
        if (invoicesFile != null)
            invoiceService.parseInvoices(clean, companyUser, invoicesFile);

        var to = invoiceService.buildAnalysis(companyUser);
        buildLearningAnalysis.apply(companyUser, to, 3);

        return to;
    }
    //endregion

    //region Operations for the initialization or updating of the indicative 4 offer
    private CompanySpedTO processLearningFour(String cnpj,
                                              boolean clean,
                                              CompanyUser companyUser,
                                              MultipartFile[] spedFiles) throws UnprocessableEntityException {
        if (spedFiles != null && spedFiles.length > 0) {
            var speds = spedService.readSpedFiles(cnpj, spedFiles);

            for (Sped sped : speds)
                validateSpedFile(companyUser.getSpeds(), sped);

            companyUser.withSpeds(speds, clean);
            scoreAnalysisService.updateResumeScore(companyUser, STRENGTH, HABITUALITY);
        }

        return bindToCompanySped.apply(cnpj, companyUser.getSpeds());
    }
    //endregion

    //region Performs the start of indicative proposals three and four, and uploads duplicate balance sheet and
    // nfe documents
    private void initLearningThreeAndFour(@NonNull CompanyUser companyUser,
                                          Integer avgReceiptTerm,
                                          MultipartFile invoicesFile,
                                          MultipartFile spedBalanceFile,
                                          MultipartFile[] spedFiles,
                                          MultipartFile[] nfeDuplicatesFiles) throws UnprocessableEntityException {
        var cnpj = companyUser.getCompany().getMainInfo().getCnpj();

        if (invoicesFile != null) {
            var avg = avgReceiptTerm == null
                    ? 1
                    : avgReceiptTerm;

            companyUser = companyUser
                    .withInitIndicativeOfferThree(true)
                    .withAvgReceiptTermInvoices(avg);

            invoiceService.parseInvoices(true, companyUser, invoicesFile);
            companyUser = confirmLearningOffer(companyUser, 3);
        }

        if (spedFiles != null && spedFiles.length > 0) {
            processLearningFour(cnpj, true, companyUser.withInitIndicativeOfferFour(true), spedFiles);
            companyUser = confirmLearningOffer(companyUser
                    .withInitIndicativeOfferFour(true), 4);
        }

        uploadDocumentsForSpedAndNfe(companyUser, spedBalanceFile, nfeDuplicatesFiles);
    }
    //endregion


    //region Calculates the indicative offer according to the learned one, and based on the user's authorization level,
    // the status of the offer is changed to requested
    private void toOfferSelectedByUserRole(CompanyUser companyUser, int number) throws UnprocessableEntityException {
        var authority = userService.getAuthority();
        if (authority.equals(BACKOFFICE) || authority.equals(SECURITY)) {
            companyUser = confirmLearningOffer(companyUser, number);
            switch (number) {
                case 1 -> companyUser.getIndicativeOfferOne().setState(SELECTED);
                case 2 -> companyUser.getIndicativeOfferTwo().setState(SELECTED);
                case 3 -> companyUser.getIndicativeOfferThree().setState(SELECTED);
                default -> companyUser.getIndicativeOfferFour().setState(SELECTED);
            }
        }
    }
    //endregion

    private void uploadDocumentsForSpedAndNfe(@NonNull CompanyUser companyUser,
                                              MultipartFile spedBalance,
                                              MultipartFile[] nfeFiles) throws UnprocessableEntityException {
        var cnpj = companyUser.getCompany().getMainInfo().getCnpj();

        if (nfeFiles != null && nfeFiles.length > 0) {
            var directories = directoryService.updloadNfeDuplicates(companyUser, nfeFiles, NFE_DUPLICITY);

            if (!companyUser.getNfeDuplicity().isEmpty())
                directoryService.deleteFiles(companyUser.getNfeDuplicity());

            companyUser.withNfeDuplicates(directories);

            if (companyUser.getIndicativeOfferThree().getState().equals(WITHOUT_OFFER)) {
                companyUser.setInitIndicativeOfferThree(true);
                companyUser.getIndicativeOfferThree().setState(WITH_OFFER);
                buildLearningAnalysis.apply(companyUser, new CompanyLearning3TO().withCnpj(cnpj), 3);
            }
        }

        if (spedBalance != null) {
            var directory = directoryService.uploadFile(cnpj, spedBalance, BALANCETE);

            if (companyUser.getSpedDocument() != null)
                directoryService.delete(companyUser.getSpedDocument().getUrl());

            companyUser.setSpedDocument(directory);

            if (companyUser.getIndicativeOfferFour().getState().equals(WITHOUT_OFFER)) {
                companyUser.setInitIndicativeOfferFour(true);
                companyUser.getIndicativeOfferFour().setState(WITH_OFFER);
                buildLearningAnalysis.apply(companyUser, new CompanyLearning4TO().withCnpj(cnpj), 4);
            }
        }
    }

    private CompanyUser confirmLearningOffer(CompanyUser companyUser,
                                             int learning) throws UnprocessableEntityException {
        switch (learning) {
            case 1 -> {
                companyUser.setLatestRegistrationForm(true);
                return indicativeOfferService.getIndicativeOffer(1, companyUser);
            }
            case 2 -> {
                validatePropertyGuaranteeDocumentExist(companyUser.getPropertyGuarantees());
                return indicativeOfferService.getIndicativeOffer(2, companyUser);
            }
            default -> {
                return indicativeOfferService.getIndicativeOffer(learning, companyUser);
            }
        }
    }

    private CompanyLearning4TO processIndicativeOfferFourSpedFile(CompanyUser companyUser)
            throws UnprocessableEntityException {
        confirmLearningOffer(companyUser.withInitIndicativeOfferFour(true), 4);
        toOfferSelectedByUserRole(companyUser, 4);

        var to4 = spedService.getAnalysis(companyUser);
        companyUser = companyUserService.save(buildLearningAnalysis.apply(companyUser, to4, 4));
        scoreAnalysisService.updateResumeScore(companyUser, STRENGTH);

        return to4;
    }

    private long getUserId(Long userId) {
        return userId == null
                ? ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
                : userId;
    }

}
