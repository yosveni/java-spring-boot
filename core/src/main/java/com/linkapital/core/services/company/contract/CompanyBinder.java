package com.linkapital.core.services.company.contract;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.authorization.contract.enums.AuthorizationState;
import com.linkapital.core.services.bank_nomenclature.contract.to.CompanyBankDocumentLightTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankDocumentTO;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.CompanyBankDocument;
import com.linkapital.core.services.company.contract.enums.EmitterType;
import com.linkapital.core.services.company.contract.to.CompanyLocationTO;
import com.linkapital.core.services.company.contract.to.CompanyTO;
import com.linkapital.core.services.company.contract.to.DocumentCompanyTO;
import com.linkapital.core.services.company.contract.to.SelectIndicativeOfferTO;
import com.linkapital.core.services.company.contract.to.SessionConfirmedTO;
import com.linkapital.core.services.company.datasource.domain.Cnds;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.CompanyPartners;
import com.linkapital.core.services.company.datasource.domain.LearningAnalysis;
import com.linkapital.core.services.company.datasource.domain.TaxHealth;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning1TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning2TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning3TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning4TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearningTO;
import com.linkapital.core.services.company_user.contract.to.DataInitLearning2TO;
import com.linkapital.core.services.company_user.contract.to.GenericTO;
import com.linkapital.core.services.company_user.contract.to.LightBackOfficeTO;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOffer;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferFour;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferOne;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferThree;
import com.linkapital.core.services.indicative_offer.datasource.domain.IndicativeOfferTwo;
import com.linkapital.core.services.person.contract.to.LightPersonSpouseTO;
import com.linkapital.core.services.person.contract.to.PartnerSpouseTO;
import com.linkapital.core.services.person.contract.to.PersonDocumentTO;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.property_guarantee.datasource.domain.PropertyGuarantee;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.util.functions.TriConsumer;
import com.linkapital.core.util.functions.TriFunction;
import com.linkapital.core.util.generic.GenericFilterTO;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.ACCEPTED;
import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.CANCELLED;
import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.PROGRESS;
import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.REJECTED;
import static com.linkapital.core.services.comment.contract.CommentBinder.COMMENT_BINDER;
import static com.linkapital.core.services.company.contract.enums.CompanyState.INTERESTED;
import static com.linkapital.core.services.directory.contract.DirectoryBinder.DIRECTORY_BINDER;
import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.LEARNING_OFFER_BINDER;
import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.validateState;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.OFFER_REQUEST;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.SELECTED;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.WITH_OFFER;
import static com.linkapital.core.services.person.contract.PersonBinder.PERSON_BINDER;
import static com.linkapital.core.services.property_guarantee.contract.PropertyGuaranteeBinder.PROPERTY_GUARANTEE_BINDER;
import static com.linkapital.core.services.property_guarantee.contract.PropertyGuaranteeBinder.bindCafirToListGuaranteeTO;
import static com.linkapital.core.services.property_guarantee.contract.enums.OwnerType.COMPANY;
import static com.linkapital.core.services.protest.contract.ProtestBinder.PROTEST_BINDER;
import static com.linkapital.core.services.security.contract.UserBinder.USER_BINDER;
import static com.linkapital.core.services.shared.contract.AddressBinder.ADDRESS_BINDER;
import static com.linkapital.core.util.json.JsonSerdes.convert;
import static com.linkapital.core.util.json.JsonSerdes.jsonfy;
import static java.time.LocalDate.now;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

@Mapper
public interface CompanyBinder {

    CompanyBinder COMPANY_BINDER = Mappers.getMapper(CompanyBinder.class);

    Consumer<CompanyUser> resetData = source -> {
        source.setCreditRequested(0);
        source.setInvoicingInformed(0);
        source.setInitIndicativeOfferOne(false);
        source.setInitIndicativeOfferTwo(false);
        source.setInitIndicativeOfferThree(false);
        source.setInitIndicativeOfferFour(false);
        source.setIndicativeOfferOne(null);
        source.setIndicativeOfferTwo(null);
        source.setIndicativeOfferThree(null);
        source.setIndicativeOfferFour(null);
        source.setLearningAnalysis(null);
        source.getLearningSessions().clear();
        source.getIssuedInvoices().clear();
        source.getReceivedInvoices().clear();
        source.getPropertyGuarantees().clear();
        source.getSpeds().clear();
    };

    TriConsumer<EmitterType, Directory, Company> updateCnd = (type, document, company) -> {
        if (document == null)
            return;

        if (company.getTaxHealth() == null)
            company.setTaxHealth(new TaxHealth().withTaxHealth("VERDE"));

        var cndsList = company.getTaxHealth().getCnds();
        var date = now();
        var expirationDate = now().plusMonths(6);
        cndsList
                .stream()
                .filter(cnds -> cnds.getEmitterName().equals(type))
                .findFirst()
                .ifPresentOrElse(cnds ->
                                cnds.withDocument(document)
                                        .withEmissionDate(date)
                                        .withExpirationDate(expirationDate),
                        () -> cndsList.add(
                                new Cnds()
                                        .withEmitterName(type)
                                        .withSituation("CERTIDAO NEGATIVA DE DEBITOS EMITIDA")
                                        .withDocument(document)
                                        .withEmissionDate(date)
                                        .withExpirationDate(expirationDate)
                        )
                );
    };

    Function<Company, List<Person>> getPartners = source -> source.getPartners()
            .stream()
            .map(CompanyPartners::getPerson)
            .filter(Objects::nonNull)
            .collect(toList());

    Function<User, List<String>> getCnpjListOfUser = source -> source.getCompanies()
            .stream()
            .map(companyUser -> companyUser.getCompany().getMainInfo().getCnpj())
            .toList();

    BiFunction<CompanyUser, Integer, IndicativeOffer> getIndicativeOfferByNumber = (source, value) -> switch (value) {
        case 1 -> source.getIndicativeOfferOne();
        case 2 -> source.getIndicativeOfferTwo();
        case 3 -> source.getIndicativeOfferThree();
        default -> source.getIndicativeOfferFour();
    };

    Function<Company, String> getFantasyName = source -> hasText(source.getFantasyName())
            ? source.getFantasyName()
            : source.getMainInfo().getSocialReason();

    Function<CompanyUser, String> getFantasyName2 = source -> {
        var company = source.getCompany();

        return hasText(company.getFantasyName())
                ? company.getFantasyName()
                : company.getMainInfo().getSocialReason();
    };

    BiFunction<List<Person>, PropertyGuarantee, String> find = (persons, property) -> persons
            .stream()
            .filter(person -> person.getProperties()
                    .stream()
                    .anyMatch(to -> to.getReferenceProperty() == property.getReferenceProperty())
                    || (person.getCafir() != null && person.getCafir().getPropertiesRural()
                    .stream()
                    .anyMatch(to -> to.getReferenceProperty() == property.getReferenceProperty())))
            .findFirst()
            .flatMap(to -> Optional.of(to.getCpf()))
            .orElse(null);

    TriFunction<List<LightPersonSpouseTO>, List<Person>, Collection<PropertyGuarantee>, Collection<PropertyGuarantee>>
            removePropertyGuarantee = (partners, personPartners, properties) -> {
        properties.removeIf(property -> Optional
                .ofNullable(find.apply(personPartners, property))
                .flatMap(s -> Optional
                        .of(partners
                                .stream()
                                .noneMatch(person -> person.getCpf().equals(s))))
                .orElse(false));

        return properties;
    };

    TriFunction<CompanyUser, List<Person>, List<Person>, CompanyLearning2TO> bindToLearning2TO =
            (source, personPartners, allPartners) -> {
                var company = source.getCompany();
                var partners = PERSON_BINDER.bindToPersonSpouseListTO(personPartners);
                var propertiesGuarantee = PROPERTY_GUARANTEE_BINDER.bindToPropertyGuaranteeTO(
                        removePropertyGuarantee.apply(partners, allPartners,
                                source.getPropertyGuarantees()));

                return new CompanyLearning2TO()
                        .withCnpj(company.getMainInfo().getCnpj())
                        .withProperties(PROPERTY_GUARANTEE_BINDER.bind(company.getProperties(), COMPANY))
                        .withPropertiesRural(company.getCafir() == null
                                ? emptyList()
                                : bindCafirToListGuaranteeTO.apply(company.getCafir().getPropertiesRural(), COMPANY))
                        .withPropertyGuarantees(propertiesGuarantee)
                        .withPersonPartners(partners)
                        .withPersonSpouses(PERSON_BINDER.buildSpouses(personPartners))
                        .withComments(COMMENT_BINDER.filterComments(source.getComments(), 2));
            };

    BiFunction<CompanyLearning2TO, List<Person>, DataInitLearning2TO> buildDataInitLearning2TO =
            (source, personPartners) -> {
                var exist = new ArrayList<>();
                var partnersSpouses = source.getPersonPartners()
                        .stream()
                        .map(to -> {
                            exist.add(to.getCpf());

                            return new PartnerSpouseTO()
                                    .withCpfPartner(to.getCpf())
                                    .withName(to.getName())
                                    .withMarriageRegime(to.getMarriageRegime())
                                    .withCpfSpouse(personPartners
                                            .stream()
                                            .filter(person -> person.getCpf().equals(to.getCpf()))
                                            .findFirst()
                                            .flatMap(person ->
                                                    Optional
                                                            .ofNullable(person.getSpouse())
                                                            .flatMap(spouse ->
                                                                    Optional
                                                                            .of(spouse.getCpf())))
                                            .orElse(null))
                                    .withSelected(true);
                        })
                        .collect(toList());

                partnersSpouses.addAll(personPartners
                        .stream()
                        .filter(person -> !exist.contains(person.getCpf()))
                        .map(person -> new PartnerSpouseTO()
                                .withCpfPartner(person.getCpf())
                                .withName(person.getName())
                                .withSelected(false))
                        .toList());

                return new DataInitLearning2TO()
                        .withCnpj(source.getCnpj())
                        .withPartnersSpouse(partnersSpouses);
            };

    BiFunction<CompanyUser, List<Person>, DataInitLearning2TO> bindToDataInitLearning2TO = (source, personPartners) ->
            Optional
                    .ofNullable(source.getLearningAnalysis())
                    .flatMap(learningAnalysis -> learningAnalysis.getLearningTwo() == null
                            ? Optional.empty()
                            : Optional
                                    .of(convert(learningAnalysis.getLearningTwo(), CompanyLearning2TO.class))
                                    .flatMap(companyLearning2TO ->
                                            Optional
                                                    .of(buildDataInitLearning2TO.apply(companyLearning2TO,
                                                            personPartners))))
                    .orElse(null);

    Function<CompanyUser, CompanyLearning2TO> buildLearning2TO = source -> Optional
            .ofNullable(source.getLearningAnalysis())
            .filter(learningAnalysis -> learningAnalysis.getLearningTwo() != null)
            .flatMap(learningAnalysis -> Optional
                    .ofNullable(convert(learningAnalysis.getLearningTwo(), CompanyLearning2TO.class))
                    .map(to -> {
                        var company = source.getCompany();
                        var cpfList = to.getPersonPartners()
                                .stream()
                                .map(LightPersonSpouseTO::getCpf)
                                .toList();
                        var personPartners = company.getPartners()
                                .stream()
                                .map(CompanyPartners::getPerson)
                                .filter(Objects::nonNull)
                                .toList();
                        var partners = personPartners
                                .stream()
                                .filter(person -> cpfList.contains(person.getCpf()))
                                .toList();

                        to.setPersonPartners(PERSON_BINDER.bindToPersonSpouseListTO(partners));
                        to.setPersonSpouses(PERSON_BINDER.buildSpouses(partners));
                        to.setPropertyGuarantees(PROPERTY_GUARANTEE_BINDER.bindToPropertyGuaranteeTO(
                                source.getPropertyGuarantees()));

                        return to.withComments(COMMENT_BINDER.filterComments(source.getComments(), 2));
                    }))
            .orElse(null);

    Function<CompanyUser, CompanyLearning3TO> buildLearning3TO = source -> Optional
            .ofNullable(source.getLearningAnalysis())
            .flatMap(learningAnalysis -> learningAnalysis.getLearningThree() == null
                    ? Optional.empty()
                    : Optional.of(convert(learningAnalysis.getLearningThree(), CompanyLearning3TO.class)
                            .withComments(COMMENT_BINDER.filterComments(source.getComments(), 3))
                            .withNfeDuplicity(DIRECTORY_BINDER.bind(source.getNfeDuplicity()))))
            .orElse(null);

    Function<CompanyUser, CompanyLearning4TO> buildLearning4TO = source -> Optional
            .ofNullable(source.getLearningAnalysis())
            .flatMap(learningAnalysis -> learningAnalysis.getLearningFour() == null
                    ? Optional.empty()
                    : Optional.of(convert(learningAnalysis.getLearningFour(), CompanyLearning4TO.class)
                            .withComments(COMMENT_BINDER.filterComments(source.getComments(), 4))
                            .withSpedDocument(DIRECTORY_BINDER.bind(source.getSpedDocument()))))
            .orElse(null);

    Function<CompanyUser, SessionConfirmedTO> bindToSessionConfirmedTO = source -> {
        var to = new SessionConfirmedTO();
        var learningSessions = source.getLearningSessions();
        to.setCnpj(source.getCompany().getMainInfo().getCnpj());

        if (!learningSessions.isEmpty()) {
            learningSessions.forEach(session -> {
                switch (session) {
                    case DATA -> to.setHasDataConfirm(true);
                    case PARTNER -> to.setHasPartnerConfirm(true);
                    case BILLING -> to.setHasBillingConfirm(true);
                    case FUNCTIONARY -> to.setHasFunctionaryConfirm(true);
                    case RELATIONS -> to.setHasRelationsConfirm(true);
                    case FISCAL -> to.setHasFiscalConfirm(true);
                    case DATA_PARTNER -> to.setHasDataPartnerConfirm(true);
                    case PROPERTIES -> to.setHasPropertiesConfirm(true);
                    case DATA_SPOUSE -> to.setHasDataSpouseConfirm(true);
                    case HORIZONTAL_ANALYSIS -> to.setHasHorizontalAnalysisConfirm(true);
                    case VERTICAL_ANALYSIS -> to.setHasVerticalAnalysisConfirm(true);
                    case CASH_CONVERSION -> to.setHasCashConversionConfirm(true);
                    case CASH_FLOW -> to.setHasCashFlowConfirm(true);
                    case HORIZONTAL_VERTICAL_ANALYSIS -> to.setHasHorizontalVerticalAnalysisConfirm(true);
                    case OTHERS_ANALYSIS -> to.setHasOthersAnalysisConfirm(true);
                    case LEGAL -> to.setHasLegalConfirm(true);
                    case SANCTIONS_RESTRICTIONS -> to.setHasSanctionsRestrictionConfirm(true);
                    case PROPERTIES_COMPANY -> to.setHasPropertiesCompanyConfirm(true);
                    case CNAE -> to.setHasCnaeConfirm(true);
                    case CORPORATE -> to.setHasCorporateConfirm(true);
                    case PROTEST -> to.setHasProtest(true);
                    case CRI_CRA_DEBENTURE -> to.setHasCriCraDebenture(true);
                    default -> {
                    }
                }
            });
        }

        return to;
    };

    Function<CompanyUser, DocumentCompanyTO> buildDocumentCompanyTO = source -> new DocumentCompanyTO()
            .withSpedDocument(DIRECTORY_BINDER.bind(source.getSpedDocument()))
            .withDebtDocuments(DIRECTORY_BINDER.bind(source.getDebtDocuments()))
            .withNfeDuplicity(DIRECTORY_BINDER.bind(source.getNfeDuplicity()))
            .withJucespDocuments(DIRECTORY_BINDER.bind(source.getCompany().getJucespDocuments()))
            .withCompanyBankDocuments(buildCompanyBankDocumentLightList(source.getBankDocuments()))
            .withCompanyPartners(buildCompanyPartnersDocumentTOtList(source.getCompany().getPartners()));

    Function<CompanyUser, SelectIndicativeOfferTO> buildSelectedOffer = source -> new SelectIndicativeOfferTO()
            .withOfferOne(validateState.test(source.getIndicativeOfferOne(), SELECTED))
            .withOfferTwo(validateState.test(source.getIndicativeOfferTwo(), SELECTED))
            .withOfferThree(validateState.test(source.getIndicativeOfferThree(), SELECTED))
            .withOfferFour(validateState.test(source.getIndicativeOfferFour(), SELECTED));

    TriFunction<CompanyUser, GenericTO, Integer, CompanyUser> buildLearningAnalysis = (companyUser, to, learning) -> {
        if (companyUser.getLearningAnalysis() == null)
            companyUser.setLearningAnalysis(new LearningAnalysis());

        switch (learning) {
            case 2 -> companyUser.getLearningAnalysis().setLearningTwo(jsonfy(to));
            case 3 -> companyUser.getLearningAnalysis().setLearningThree(jsonfy(to));
            case 4 -> companyUser.getLearningAnalysis().setLearningFour(jsonfy(to));
            default -> {
            }
        }

        return companyUser;
    };

    Function<CompanyUser, List<Integer>> buildOfferTypeNumbers = source -> {
        var offerTypeNumbers = new ArrayList<Integer>();

        if (validateState.test(source.getIndicativeOfferOne(), OFFER_REQUEST))
            offerTypeNumbers.add(1);
        if (validateState.test(source.getIndicativeOfferTwo(), OFFER_REQUEST))
            offerTypeNumbers.add(2);
        if (validateState.test(source.getIndicativeOfferThree(), OFFER_REQUEST))
            offerTypeNumbers.add(3);
        if (validateState.test(source.getIndicativeOfferFour(), OFFER_REQUEST))
            offerTypeNumbers.add(4);

        return offerTypeNumbers;
    };

    Function<CompanyUser, LightBackOfficeTO> buildLightBackOfficeTO = source -> {
        var company = source.getCompany();
        var offerTypes = buildOfferTypeNumbers.apply(source);

        return new LightBackOfficeTO()
                .withId(company.getId())
                .withFantasyName(company.getFantasyName())
                .withCnpj(company.getMainInfo().getCnpj())
                .withSocialReason(company.getMainInfo().getSocialReason())
                .withOpeningDate(company.getMainInfo().getOpeningDate())
                .withCreated(source.getCreated())
                .withEstimatedBilling(company.getEstimatedBilling())
                .withCreditRequested(source.getCreditRequested())
                .withInvoicingInformed(source.getInvoicingInformed())
                .withCompanyState(source.getCompanyState())
                .withSimpleNational(company.getSimpleNational())
                .withAddress(ADDRESS_BINDER.bind(company.getMainInfo().getAddress()))
                .withUser(USER_BINDER.bindToLightBackOffice(source.getUser()))
                .withOfferTypeNumbers(offerTypes)
                .withInitIndicativeOfferOne(source.isInitIndicativeOfferOne())
                .withInitIndicativeOfferTwo(source.isInitIndicativeOfferTwo())
                .withInitIndicativeOfferThree(source.isInitIndicativeOfferThree())
                .withInitIndicativeOfferFour(source.isInitIndicativeOfferFour())
                .withHasSpedBalanceDocument(source.getSpedDocument() != null)
                .withHasNfeDuplicity(!source.getNfeDuplicity().isEmpty())
                .withHasSpedDocuments(!source.getSpeds().isEmpty())
                .withHasInvoiceDocuments(!source.getIssuedInvoices().isEmpty());
    };

    Function<Page<CompanyUser>, GenericFilterTO<LightBackOfficeTO>> buildListLightBackOfficeTO = source -> {
        if (source.isEmpty())
            return new GenericFilterTO<>();

        var list = source.getContent()
                .stream()
                .map(buildLightBackOfficeTO)
                .toList();

        return new GenericFilterTO<>(list, source.getTotalElements());
    };

    BiFunction<AuthorizationState, List<CompanyClientTO>, List<CompanyClientTO>> sortByAuthorizationState =
            (state, source) -> source
                    .stream()
                    .filter(to -> state == null
                            ? to.getOwnerAuthorization() == null
                            : to.getOwnerAuthorization() != null && to.getOwnerAuthorization().getState().equals(state))
                    .sorted(comparing(CompanyClientTO::getFantasyName))
                    .collect(toList());

    Consumer<List<CompanyClientTO>> sortByDocumentPending = source -> source
            .sort(comparing((CompanyClientTO c) -> c.getBankNomenclatures()
                    .stream()
                    .filter(to -> !to.isHasDocument())
                    .count())
                    .thenComparing(CompanyClientTO::getFantasyName));

    UnaryOperator<List<CompanyClientTO>> sortListCompanyClient = source -> {
        var result = sortByAuthorizationState.apply(ACCEPTED, source);
        sortByDocumentPending.accept(result);
        result.addAll(sortByAuthorizationState.apply(PROGRESS, source));
        result.addAll(sortByAuthorizationState.apply(REJECTED, source));
        result.addAll(sortByAuthorizationState.apply(CANCELLED, source));
        result.addAll(sortByAuthorizationState.apply(null, source));

        return result;
    };

    Consumer<CompanyUser> initLearnings = companyUser -> {
        if (companyUser.getIndicativeOfferOne() == null)
            companyUser.setIndicativeOfferOne(new IndicativeOfferOne());
        if (companyUser.getIndicativeOfferTwo() == null)
            companyUser.setIndicativeOfferTwo(new IndicativeOfferTwo());
        if (companyUser.getIndicativeOfferThree() == null)
            companyUser.setIndicativeOfferThree(new IndicativeOfferThree());
        if (companyUser.getIndicativeOfferFour() == null)
            companyUser.setIndicativeOfferFour(new IndicativeOfferFour());
    };

    BiConsumer<CompanyUser, User> set = (companyUser, user) -> {
        companyUser.getCompany().setRegistered(true);
        companyUser.setUser(user);
        companyUser.setCompanyState(INTERESTED);
        initLearnings.accept(companyUser);
    };

    BiFunction<Company, User, CompanyUser> setAndGet = (company, user) -> {
        var companyUser = new CompanyUser()
                .withCompany(company);
        set.accept(companyUser, user);

        return companyUser;
    };

    static void setIndicativeOfferState(CompanyUser companyUser,
                                        @NonNull SelectIndicativeOfferTO to) throws UnprocessableEntityException {
        if (to.isOfferOne())
            setIndicativeOfferState(companyUser.getIndicativeOfferOne(), 1);

        if (to.isOfferTwo())
            setIndicativeOfferState(companyUser.getIndicativeOfferTwo(), 2);

        if (to.isOfferThree())
            setIndicativeOfferState(companyUser.getIndicativeOfferThree(), 3);

        if (to.isOfferFour())
            setIndicativeOfferState(companyUser.getIndicativeOfferFour(), 4);
    }

    static void setIndicativeOfferState(IndicativeOffer indicativeOffer,
                                        int learningNumber) throws UnprocessableEntityException {
        if (validateState.test(indicativeOffer, WITH_OFFER))
            indicativeOffer.setState(SELECTED);
        else
            throw new UnprocessableEntityException(msg("company.learning.has.no.offer", learningNumber));
    }

    static List<CompanyBankDocumentLightTO> buildCompanyBankDocumentLightList(
            @NonNull Set<CompanyBankDocument> bankDocuments) {
        return bankDocuments.stream()
                .map(companyBankDocument -> new CompanyBankDocumentLightTO()
                        .withId(companyBankDocument.getId())
                        .withDescription(companyBankDocument.getBankNomenclature().getDescription())
                        .withDirectories(DIRECTORY_BINDER.bind(companyBankDocument.getDirectories())))
                .toList();
    }

    static List<PersonDocumentTO> buildCompanyPartnersDocumentTOtList(@NonNull Set<CompanyPartners> companyPartners) {
        return companyPartners
                .stream()
                .map(CompanyPartners::getPerson)
                .filter(Objects::nonNull)
                .map(person -> {
                    var personDocument = new PersonDocumentTO()
                            .withId(person.getId())
                            .withName(person.getName())
                            .withIrpfDocuments(DIRECTORY_BINDER.bind(person.getIrpfDocuments()))
                            .withIrpfReceiptDocuments(DIRECTORY_BINDER.bind(person.getIrpfReceiptDocuments()));

                    if (person.getSpouse() != null) {
                        var spouse = person.getSpouse();
                        personDocument
                                .withSpouseName(spouse.getName())
                                .withSpouseIrpfDocuments(DIRECTORY_BINDER.bind(spouse.getIrpfDocuments()))
                                .withSpouseIrpfReceiptDocuments(DIRECTORY_BINDER.bind(spouse.getIrpfReceiptDocuments()));
                    }

                    return personDocument;
                })
                .toList();
    }

    @Mapping(target = "fantasyName", expression = "java(getFantasyName.apply(source))")
    @Mapping(target = "invoicingInformed", ignore = true)
    @Mapping(target = "protestInformation", ignore = true)
    @Mapping(target = "indicativeOfferOne", ignore = true)
    @Mapping(target = "indicativeOfferTwo", ignore = true)
    @Mapping(target = "indicativeOfferThree", ignore = true)
    @Mapping(target = "indicativeOfferFour", ignore = true)
    @Mapping(target = "debtDocuments", ignore = true)
    @Mapping(target = "comments", ignore = true)
    CompanyLearning1TO bindToLearning1TO(Company source);

    @Mapping(target = "id", expression = "java(source.getCompany().getId())")
    @Mapping(target = "cnpj", expression = "java(source.getCompany().getMainInfo().getCnpj())")
    @Mapping(target = "fantasyName", expression = "java(getFantasyName2.apply(source))")
    @Mapping(target = "socialReason", expression = "java(source.getCompany().getMainInfo().getSocialReason())")
    @Mapping(target = "scr", expression = "java(source.getCompany().isScr())")
    @Mapping(target = "hasAnOwner", expression = "java(source.getCompany().getUserOwnerId() != null)")
    @Mapping(target = "hasSpedBalanceDocument", expression = "java(source.getSpedDocument() != null)")
    @Mapping(target = "hasNfeDuplicity", expression = "java(!source.getNfeDuplicity().isEmpty())")
    @Mapping(target = "hasSpedDocuments", expression = "java(!source.getSpeds().isEmpty())")
    @Mapping(target = "hasInvoiceDocuments", expression = "java(!source.getIssuedInvoices().isEmpty())")
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "indicativeOfferOne", ignore = true)
    @Mapping(target = "indicativeOfferTwo", ignore = true)
    @Mapping(target = "indicativeOfferThree", ignore = true)
    @Mapping(target = "indicativeOfferFour", ignore = true)
    CompanyClientTO bindClientTO(CompanyUser source);

    @Mapping(target = "id", expression = "java(source.getId())")
    @Mapping(target = "created", expression = "java(source.getCreated())")
    @Mapping(target = "cnpj", expression = "java(source.getMainInfo().getCnpj())")
    @Mapping(target = "fantasyName", expression = "java(getFantasyName.apply(source))")
    @Mapping(target = "socialReason", expression = "java(source.getMainInfo().getSocialReason())")
    @Mapping(target = "openingDate", expression = "java(source.getMainInfo().getOpeningDate())")
    LightBackOfficeTO bindToLightBackOfficeWitchUserTO(Company source, User user);

    @Mapping(target = "fantasyName", expression = "java(getFantasyName.apply(source))")
    @Mapping(target = "protestInformation", ignore = true)
    @Mapping(target = "indicativeOfferOne", ignore = true)
    @Mapping(target = "indicativeOfferTwo", ignore = true)
    @Mapping(target = "indicativeOfferThree", ignore = true)
    @Mapping(target = "indicativeOfferFour", ignore = true)
    CompanyLearningTO bindToLearningTO(Company source);

    List<CompanyLocationTO> bind(List<Company> source);

    List<CompanyBankDocumentTO> bindToCompanyBankDocumentListTO(Set<CompanyBankDocument> source);

    default CompanyTO buildCompanyTO(CompanyUser source) {
        return new CompanyTO()
                .withCompanyLearning1(buildLearningTO(source))
                .withCompanyLearning2(buildLearning2TO.apply(source))
                .withCompanyLearning3(buildLearning3TO.apply(source))
                .withCompanyLearning4(buildLearning4TO.apply(source));
    }

    default CompanyLearningTO buildLearningTO(@NonNull CompanyUser source) {
        var company = source.getCompany();
        return bindToLearningTO(company)
                .withInvoicingInformed(source.getInvoicingInformed())
                .withProtestInformation(PROTEST_BINDER.bind(company.getProtestInformation()))
                .withIndicativeOfferOne(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferOne()))
                .withIndicativeOfferTwo(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferTwo()))
                .withIndicativeOfferThree(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferThree()))
                .withIndicativeOfferFour(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferFour()))
                .withDebtDocuments(DIRECTORY_BINDER.bind(source.getDebtDocuments()))
                .withComments(COMMENT_BINDER.filterComments(source.getComments(), 1));
    }

    default CompanyLearning1TO buildLearning1TO(@NonNull CompanyUser source) {
        var company = source.getCompany();
        return bindToLearning1TO(company)
                .withInvoicingInformed(source.getInvoicingInformed())
                .withProtestInformation(PROTEST_BINDER.bind(company.getProtestInformation()))
                .withIndicativeOfferOne(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferOne()))
                .withIndicativeOfferTwo(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferTwo()))
                .withIndicativeOfferThree(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferThree()))
                .withIndicativeOfferFour(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferFour()))
                .withDebtDocuments(DIRECTORY_BINDER.bind(source.getDebtDocuments()))
                .withComments(COMMENT_BINDER.filterComments(source.getComments(), 1));
    }

    default CompanyClientTO bindToClientTO(CompanyUser source) {
        return bindClientTO(source)
                .withIndicativeOfferOne(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferOne()))
                .withIndicativeOfferTwo(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferTwo()))
                .withIndicativeOfferThree(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferThree()))
                .withIndicativeOfferFour(LEARNING_OFFER_BINDER.bind(source.getIndicativeOfferFour()));
    }

    default List<LightBackOfficeTO> bindToLightBackOfficeListTO(@NonNull List<Company> source, User user) {
        return source
                .stream()
                .map(company -> bindToLightBackOfficeWitchUserTO(company, user))
                .toList();
    }

}
