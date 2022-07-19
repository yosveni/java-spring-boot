package com.linkapital.core.services.company.impl;

import com.linkapital.core.events.user.UserEventsPublisher;
import com.linkapital.core.exceptions.CnpjNotFoundException;
import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.CompanyService;
import com.linkapital.core.services.company.contract.to.CompanyLocationTO;
import com.linkapital.core.services.company.datasource.CompanyRepository;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company_user.contract.to.InitLearningOneTO;
import com.linkapital.core.services.company_user.contract.to.LightBackOfficeTO;
import com.linkapital.core.services.company_user.datasource.CompanyUserRepository;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.credit_information.CreditInformationService;
import com.linkapital.core.services.credit_information.contract.to.CreditInformationTO;
import com.linkapital.core.services.credit_information.datasource.domain.CreditInformation;
import com.linkapital.core.services.directory.DirectoryService;
import com.linkapital.core.services.external_information.ExternalInformationService;
import com.linkapital.core.services.integrations.NeoWaySearchService;
import com.linkapital.core.services.method_k.ScoreAnalysisService;
import com.linkapital.core.services.offer.contract.enums.FilterOfferType;
import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.services.shared.CompanyConverter;
import com.linkapital.core.util.generic.GenericFilterTO;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.company.contract.CompanyBinder.COMPANY_BINDER;
import static com.linkapital.core.services.company.contract.CompanyBinder.buildListLightBackOfficeTO;
import static com.linkapital.core.services.company.contract.CompanyBinder.getCnpjListOfUser;
import static com.linkapital.core.services.company.contract.CompanyBinder.resetData;
import static com.linkapital.core.services.company.contract.CompanyBinder.set;
import static com.linkapital.core.services.company.contract.CompanyBinder.setAndGet;
import static com.linkapital.core.services.company.validator.CompanyValidator.validateElementNull;
import static com.linkapital.core.services.company.validator.CompanyValidator.validateListContaints;
import static com.linkapital.core.services.company.validator.CompanyValidator.validateListEmpty;
import static com.linkapital.core.services.company.validator.CompanyValidator.validateListNotEmpty;
import static com.linkapital.core.services.credit_information.contract.CreditInformationBinder.CREDIT_INFORMATION_BINDER;
import static com.linkapital.core.services.method_k.contract.enums.ScoreType.HABITUALITY;
import static com.linkapital.core.services.method_k.contract.enums.ScoreType.REGISTER;
import static com.linkapital.core.util.ParseUtil.parseList;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNullElseGet;
import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final List<CompanyConverter> companyConverters;
    private final List<CompanyConverter> companyConvertersDependents;
    private final CompanyRepository companyRepository;
    private final CompanyUserRepository companyUserRepository;
    private final DirectoryService directoryService;
    private final PersonService personService;
    private final NeoWaySearchService neoWaySearchService;
    private final UserEventsPublisher userEventsPublisher;
    private final CreditInformationService creditInformationService;
    private final ExternalInformationService externalInformationService;
    private final ScoreAnalysisService scoreAnalysisService;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              CompanyUserRepository companyUserRepository,
                              DirectoryService directoryService,
                              PersonService personService,
                              NeoWaySearchService neoWaySearchService,
                              UserEventsPublisher userEventsPublisher,
                              CreditInformationService creditInformationService,
                              ExternalInformationService externalInformationService,
                              ScoreAnalysisService scoreAnalysisService) {
        this.companyUserRepository = companyUserRepository;
        this.personService = personService;
        this.companyRepository = companyRepository;
        this.directoryService = directoryService;
        this.neoWaySearchService = neoWaySearchService;
        this.userEventsPublisher = userEventsPublisher;
        this.creditInformationService = creditInformationService;
        this.externalInformationService = externalInformationService;
        this.scoreAnalysisService = scoreAnalysisService;
        this.companyConverters = new ArrayList<>();
        this.companyConvertersDependents = new ArrayList<>();
    }

    @Override
    public GenericFilterTO<LightBackOfficeTO> getListLightBackOfficeTO(FilterOfferType type,
                                                                       String filter,
                                                                       Integer page,
                                                                       Integer size,
                                                                       String sort,
                                                                       String order)
            throws UnprocessableEntityException {
        return buildListLightBackOfficeTO.apply(getByFilterGlobal(type, filter, page, size, sort, order));
    }

    @Override
    public Page<CompanyUser> getByFilterGlobal(FilterOfferType type,
                                               String filter,
                                               Integer page,
                                               Integer size,
                                               String sort,
                                               String order) throws UnprocessableEntityException {
        return companyRepository.getByFilterGlobal(type, filter, of(page, size), sort, order);
    }

    @Override
    public List<Company> getByCnpjIn(List<String> cnpjList) {
        return companyRepository.findAllByMainInfo_CnpjIn(cnpjList);
    }

    @Override
    public List<Person> getAllPartners(String cnpj) throws ResourceNotFoundException {
        var company = getByCnpj(cnpj);

        return company.getPartners().isEmpty()
                ? Collections.emptyList()
                : personService.getPersonPartners(company);
    }

    @Override
    public Company getByCnpj(String cnpj) throws ResourceNotFoundException {
        return companyRepository
                .findByMainInfo_Cnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException(msg("company.cnpj.not.found", cnpj)));
    }

    @Override
    public void resetOwner(String cnpj) throws ResourceNotFoundException {
        var company = getByCnpj(cnpj);

        company.getCompanyUsers().forEach(companyUser -> {
            clearDocuments(companyUser);
            resetData.accept(companyUser);
            companyUser.getUser().getCompanies().remove(companyUser);
            userEventsPublisher.save(companyUser.getUser());
            companyUserRepository.delete(companyUser);
        });

        company.getCompanyUsers().clear();
        save(company);
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void delete(String cnpj) throws ResourceNotFoundException {
        Optional
                .of(getByCnpj(cnpj))
                .ifPresent(this::delete);
    }

    private void delete(@NonNull Company company) {
        companyRepository.delete(company);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        companyRepository
                .findById(id)
                .ifPresent(this::delete);
    }

    @Override
    public List<String> getAllCnpjRepeat() {
        var all = companyRepository.findAll();
        var find = new ArrayList<String>();

        for (var c : all) {
            all
                    .stream()
                    .filter(company -> company.getMainInfo().getCnpj().equals(c.getMainInfo().getCnpj())
                            && !company.getId().equals(c.getId()))
                    .findFirst()
                    .ifPresent(company -> {
                        if (!find.contains(company.getMainInfo().getCnpj()))
                            find.add(company.getMainInfo().getCnpj());
                    });
        }

        return find;
    }

    @Override
    public List<CompanyLocationTO> getAllCompaniesInRadioLocation(double latitude, double longitude, double radius) {
        return COMPANY_BINDER.bind(companyRepository.findAllInRadioLocation(latitude, longitude, radius));
    }

    @Override
    public List<CreditInformationTO> updateCreditsInformationByCnpj(String cnpj) throws UnprocessableEntityException {
        try {
            var company = getByCnpj(cnpj);
            //Todo activar luego
//            validateScr(company.isScr());

            var creditInformation = creditInformationService.getCnpjCreditInformationData(cnpj);
            company.getCreditsInformation().addAll(creditInformation);
            company = companyRepository.saveAndFlush(company);
            creditInformation = company.getCreditsInformation()
                    .stream()
                    .sorted(comparing(CreditInformation::getConsultDate).reversed())
                    .toList();

            return CREDIT_INFORMATION_BINDER.bind(creditInformation);
        } catch (ResourceNotFoundException | CnpjNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }

    @Override
    public void addConverter(CompanyConverter companyConverter) {
        this.companyConverters.add(companyConverter);
    }

    @Override
    public void addConverterDependent(CompanyConverter companyConverter) {
        this.companyConvertersDependents.add(companyConverter);
    }

    @Override
    public void saveAll(@NotNull List<Company> companies) {
        companyRepository.saveAll(companies);
    }

    @Override
    public List<Company> buildRelations(User user,
                                        @NonNull List<String> cnpjList,
                                        List<Company> companyDatabaseList) throws UnprocessableEntityException {
        var companies = new ArrayList<Company>();
        var companyUsers = new ArrayList<CompanyUser>();
        var cnpjNotFounds = new ArrayList<String>();

        for (String cnpj : cnpjList) {
            var company = companyDatabaseList
                    .stream()
                    .filter(company1 -> cnpj.equals(company1.getMainInfo().getCnpj()))
                    .findFirst()
                    .orElse(null);
            try {
                company = populateCompany(cnpj, requireNonNullElseGet(company, Company::new));
            } catch (UnprocessableEntityException e) {
                cnpjNotFounds.add(cnpj);
            }

            if (company != null) {
                var companyUser = new CompanyUser().withCompany(company);
                set.accept(companyUser, user);
                scoreAnalysisService.updateResumeScore(companyUser, REGISTER, HABITUALITY);
                company.getCompanyUsers().add(companyUser);
                companies.add(company);
                companyUsers.add(companyUser);
            }
        }

        validateListEmpty(companyUsers, msg("company.list.not.found"));
        companyUserRepository.saveAll(companyUsers);
        validateListNotEmpty(cnpjNotFounds, msg("company.files.following.files.found",
                parseList.apply(cnpjNotFounds)));

        return companies;
    }

    @Override
    public Company getFromExternalApi(String cnpj, @NotNull Company company) throws UnprocessableEntityException {
        Map data;
        try {
            data = neoWaySearchService.getCnpjData(cnpj);
        } catch (CnpjNotFoundException e) {
            throw new UnprocessableEntityException(msg("company.cnpj.info.not.found.by.ia"));
        }

        company.setClient(true);
        applyCompanyConverters(company, data, companyConverters);
        company = save(company);
        applyCompanyConverters(company, data, companyConvertersDependents);

        return company;
    }

    @Override
    public CompanyUser processInitLearningOne(User user, @NonNull InitLearningOneTO to)
            throws UnprocessableEntityException {

        var cnpj = to.getCnpj();
        var userCnpjList = getCnpjListOfUser.apply(user);

        validateListContaints(userCnpjList, cnpj, msg("company.already.exist.for.client", user.getEmail()));
        var company = populateCompany(cnpj, getByCnpjAux(cnpj));
        company.setScr(true);

        return setAndGet.apply(company, user);
    }

    @Override
    public Company getByCnpjAux(String cnpj) {
        return companyRepository
                .findByMainInfo_Cnpj(cnpj)
                .orElse(null);
    }

    @Override
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    //region Apply the converters to the company
    private void applyCompanyConverters(Company company,
                                        Map<String, Object> data,
                                        @NonNull List<CompanyConverter> companyConverters) {
        for (CompanyConverter companyConverter : companyConverters) {
            try {
                company = companyConverter.convert(company, data);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
    }
    //endregion

    //region Returns the company obtained from the external API
    private @NonNull Company populateCompany(String cnpj, Company company) throws UnprocessableEntityException {
        if (company == null)
            company = getFromExternalApi(cnpj, new Company());
        else if (!company.isClient())
            company = getFromExternalApi(cnpj, company);

        validateElementNull(company, msg("company.cnpj.not.found", cnpj));
        externalInformationService.populateExternalInformation(company);

        return company;
    }
    //endregion

    //region Delete stored company documents
    private void clearDocuments(@NonNull CompanyUser companyUser) {
        if (!companyUser.getComments().isEmpty()) {
            companyUser.getComments().forEach(comment -> {
                if (!comment.getAttachments().isEmpty())
                    directoryService.deleteFiles(comment.getAttachments());
            });

            companyUser.getComments().clear();
        }

        if (!companyUser.getDebtDocuments().isEmpty())
            directoryService.deleteFiles(companyUser.getDebtDocuments());

        if (!companyUser.getBankDocuments().isEmpty())
            companyUser.getBankDocuments().forEach(bankDocument -> {
                if (!bankDocument.getDirectories().isEmpty())
                    directoryService.deleteFiles(bankDocument.getDirectories());
            });
    }
    //endregion

}
