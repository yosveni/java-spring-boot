package com.linkapital.core.services.authorization.impl;

import com.linkapital.core.exceptions.CnpjNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.authorization.AuthorizationQuestionService;
import com.linkapital.core.services.authorization.OwnerAuthorizationService;
import com.linkapital.core.services.authorization.contract.to.answer.AuthorizationAnswerForAgentTO;
import com.linkapital.core.services.authorization.contract.to.answer.AuthorizationAnswerForOwnerTO;
import com.linkapital.core.services.authorization.contract.to.answer.CreateAuthorizationAnswerTO;
import com.linkapital.core.services.authorization.contract.to.authorization.CancelAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.authorization.ClientAuthorizationDataTO;
import com.linkapital.core.services.authorization.contract.to.authorization.InitOwnerAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.authorization.OwnerAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;
import com.linkapital.core.services.authorization.datasource.OwnerAuthorizationRepository;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationAnswer;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationQuestion;
import com.linkapital.core.services.authorization.datasource.domain.OwnerAuthorization;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company_user.CompanyUserService;
import com.linkapital.core.services.company_user.datasource.CompanyUserRepository;
import com.linkapital.core.services.credit_information.CreditInformationService;
import com.linkapital.core.services.notification.EmailService;
import com.linkapital.core.services.notification.WebsocketService;
import com.linkapital.core.services.security.datasource.domain.User;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.authorization.contract.AuthorizationQuestionBinder.AUTHORIZATION_QUESTION_BINDER;
import static com.linkapital.core.services.authorization.contract.OwnerAuthorizationBinder.OWNER_AUTHORIZATION_BINDER;
import static com.linkapital.core.services.authorization.contract.OwnerAuthorizationBinder.build;
import static com.linkapital.core.services.authorization.contract.OwnerAuthorizationBinder.buildAuthorizationNotification;
import static com.linkapital.core.services.authorization.contract.OwnerAuthorizationBinder.setState;
import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.ACCEPTED;
import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.CANCELLED;
import static com.linkapital.core.services.authorization.validator.OwnerAuthorizationValidator.validateQuestionAndAnswers;
import static com.linkapital.core.services.authorization.validator.OwnerAuthorizationValidator.validateState;
import static com.linkapital.core.services.notification.WebsocketService.AUTHORIZATION_STATUS_CHANGED;
import static com.linkapital.core.services.notification.contract.enums.EmailType.NORMAL;
import static com.linkapital.core.services.notification.contract.enums.WebsocketBroker.TOPIC;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.StringUtils.hasText;

@Service
@Transactional
public class OwnerAuthorizationServiceImpl implements OwnerAuthorizationService {

    private static final String PROD_LINK = "https://main.linkapital.com.br/client/owner-approval/";
    private static final String TEST_LINK = "https://testing.linkapital.com.br/client/owner-approval/";
    private static final String LOCAL_LINK = "http://localhost:4200/client/owner-approval/";
    private static final Logger log = getLogger(OwnerAuthorizationServiceImpl.class);
    private final String profile;
    private final OwnerAuthorizationRepository ownerAuthorizationRepository;
    private final CompanyUserService companyUserService;
    private final CompanyUserRepository companyUserRepository;
    private final CreditInformationService creditInformationService;
    private final AuthorizationQuestionService authorizationQuestionService;
    private final EmailService emailService;
    private final WebsocketService webSocketService;

    public OwnerAuthorizationServiceImpl(@Value("${spring.profiles.active}") String profile,
                                         EmailService emailService,
                                         OwnerAuthorizationRepository ownerAuthorizationRepository,
                                         CompanyUserService companyUserService,
                                         CompanyUserRepository companyUserRepository,
                                         CreditInformationService creditInformationService,
                                         AuthorizationQuestionService authorizationQuestionService,
                                         WebsocketService webSocketService) {
        this.profile = profile;
        this.emailService = emailService;
        this.ownerAuthorizationRepository = ownerAuthorizationRepository;
        this.companyUserService = companyUserService;
        this.companyUserRepository = companyUserRepository;
        this.creditInformationService = creditInformationService;
        this.authorizationQuestionService = authorizationQuestionService;
        this.webSocketService = webSocketService;
    }

    @Override
    public void initForAgent(@NonNull InitOwnerAuthorizationTO to) throws UnprocessableEntityException {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var companyUser = companyUserService.get(to.getCnpj(), user.getId());
        var ownerAuthorization = OWNER_AUTHORIZATION_BINDER.bind(to)
                .withToken(randomAlphanumeric(50))
                .withCompanyUser(companyUser);
        var company = companyUser.getCompany();
        var fantasyName = company.getFantasyName();
        var companyName = hasText(fantasyName)
                ? fantasyName
                : company.getMainInfo().getSocialReason();

        companyUserRepository.save(companyUser
                .withOwnerAuthorization(ownerAuthorization));
        sendEmail(companyName, user.getName(), ownerAuthorization.getName(), ownerAuthorization.getEmail(),
                ownerAuthorization.getToken());
    }

    @Override
    public void initForOwner(@NonNull AuthorizationAnswerForOwnerTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj());
        var ownerAuthorization = build.apply(to, companyUser);

        if (ownerAuthorization.getState().equals(ACCEPTED))
            updateCompanyUser(ownerAuthorization);

        companyUserRepository.save(companyUser
                .withOwnerAuthorization(ownerAuthorization));
    }

    @Override
    public OwnerAuthorizationTO answerQuestions(@NonNull AuthorizationAnswerForAgentTO to)
            throws UnprocessableEntityException {

        var ownerAuthorization = getByToken(to.getToken());
        var questionSize = 0;

        validateState(ownerAuthorization.getState());

        if (!to.getAnswers().isEmpty()) {
            var questions = authorizationQuestionService.getAll();
            questionSize = questions.size();
            updateAnswers(ownerAuthorization, to, questions);
        }

        ownerAuthorization = setState.apply(questionSize, to, ownerAuthorization);

        var accepted = false;
        if (ownerAuthorization.getState().equals(ACCEPTED)) {
            updateCompanyUser(ownerAuthorization);
            accepted = true;
        }

        webSocketService.dispatch(
                ownerAuthorization.getCompanyUser().getUser().getEmail(),
                TOPIC,
                AUTHORIZATION_STATUS_CHANGED,
                accepted
                        ? msg("authorization.status.accepted")
                        : msg("authorization.status.cancelled"),
                buildAuthorizationNotification.apply(ownerAuthorization)
        );

        return OWNER_AUTHORIZATION_BINDER.bind(save(ownerAuthorization));
    }

    @Override
    public void cancel(@NonNull CancelAuthorizationTO to) throws UnprocessableEntityException {
        var ownerAuthorization = getByToken(to.getToken());

        validateState(ownerAuthorization.getState());
        ownerAuthorization = save(ownerAuthorization
                .withCancelledReason(to.getCancelledReason())
                .withState(CANCELLED));

        webSocketService.dispatch(
                ownerAuthorization.getCompanyUser().getUser().getEmail(),
                TOPIC,
                AUTHORIZATION_STATUS_CHANGED,
                msg("authorization.status.cancelled"),
                buildAuthorizationNotification.apply(ownerAuthorization)
        );
    }

    @Override
    public ClientAuthorizationDataTO getForClient(String token) throws UnprocessableEntityException {
        var ownerAuthorization = getByToken(token);
        validateState(ownerAuthorization.getState());

        return OWNER_AUTHORIZATION_BINDER.bind(ownerAuthorization, authorizationQuestionService.getAll());
    }

    @Override
    public OwnerAuthorizationTO getByCompanyUser(String cnpj, long userId) throws UnprocessableEntityException {
        return Optional
                .of(companyUserService.get(cnpj, userId))
                .map(companyUser -> OWNER_AUTHORIZATION_BINDER.bind(companyUser.getOwnerAuthorization()))
                .orElseThrow(() -> new UnprocessableEntityException(msg("owner.authorization.not.found", cnpj)));
    }

    @Override
    public void delete(long id) throws UnprocessableEntityException {
        Optional
                .of(getById(id))
                .ifPresent(ownerAuthorization -> companyUserService.save(ownerAuthorization.getCompanyUser()
                        .withOwnerAuthorization(null)));
    }

    @Override
    public OwnerAuthorization getById(long id) throws UnprocessableEntityException {
        return ownerAuthorizationRepository
                .findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("owner.authorization.id.not.found", id)));
    }

    //region Send email for company owner
    private void sendEmail(String companyName, String agentName, String ownerName, String ownerEmail, String token) {
        emailService.send(
                NORMAL,
                ownerEmail,
                msg("owner.authorization.k.agent.email.title", companyName),
                msg("owner.authorization.k.agent.email.content", ownerName, agentName, getUrl(token)));
    }
    //endregion

    //region Update Company - User data
    private void updateCompanyUser(@NonNull OwnerAuthorization ownerAuthorization) {
        var companyUser = ownerAuthorization.getCompanyUser();
        var user = companyUser.getUser();
        var company = companyUser.getCompany();

        companyUser.setOwner(true);
        company.setUserOwnerId(user.getId());
        setCreditInformation(company);
    }
    //endregion

    //region Get the Credit Information by CNPJ
    private void setCreditInformation(@NonNull Company company) {
        var cnpj = company.getMainInfo().getCnpj();
        try {
            var creditInformation = creditInformationService.getCnpjCreditInformationData(cnpj);
            company.getCreditsInformation().addAll(creditInformation);
        } catch (CnpjNotFoundException | UnprocessableEntityException e) {
            log.error(e.getMessage());
        }
    }
    //endregion

    //region Save owner authorization
    private OwnerAuthorization getByToken(String token) throws UnprocessableEntityException {
        return ownerAuthorizationRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new UnprocessableEntityException(msg("owner.authorization.token.not.found", token)));
    }
    //endregion

    //region Update authorization responses through the total number of questions registered in the system.
    // After performing the update, the total number of questions in the system is returned
    private void updateAnswers(@NonNull OwnerAuthorization ownerAuthorization,
                               @NonNull AuthorizationAnswerForAgentTO to,
                               List<AuthorizationQuestionTO> questions) throws UnprocessableEntityException {
        var answers = to.getAnswers();
        validateQuestionAndAnswers(questions, answers);

        if (!ownerAuthorization.getAnswers().isEmpty())
            ownerAuthorization.getAnswers().clear();

        for (CreateAuthorizationAnswerTO data : answers)
            ownerAuthorization.getAnswers().add(new AuthorizationAnswer()
                    .withYesAnswer(data.isAnswer())
                    .withQuestion(getQuestion(data.getQuestionId(), questions)));
    }
    //endregion

    //region Save owner authorization
    private @NonNull OwnerAuthorization save(OwnerAuthorization ownerAuthorization) {
        return ownerAuthorizationRepository.save(ownerAuthorization);
    }
    //endregion

    //region Search for a question by its id in the list of questions passed by parameter
    private AuthorizationQuestion getQuestion(long id,
                                              @NonNull List<AuthorizationQuestionTO> questions)
            throws UnprocessableEntityException {

        return questions
                .stream()
                .filter(authorizationQuestion -> authorizationQuestion.getId() == id)
                .findFirst()
                .map(AUTHORIZATION_QUESTION_BINDER::bind)
                .orElseThrow(() ->
                        new UnprocessableEntityException(msg("owner.authorization.question.id.not.found", id)));
    }
    //endregion

    //region Generate the url to send by email
    private String getUrl(String token) {
        return switch (profile) {
            case "prod" -> PROD_LINK + token;
            case "test" -> TEST_LINK + token;
            default -> LOCAL_LINK + token;
        };
    }
    //endregion

}
