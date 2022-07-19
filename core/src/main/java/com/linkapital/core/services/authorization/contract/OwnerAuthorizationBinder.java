package com.linkapital.core.services.authorization.contract;

import com.linkapital.core.services.authorization.contract.to.answer.AuthorizationAnswerForAgentTO;
import com.linkapital.core.services.authorization.contract.to.answer.AuthorizationAnswerForOwnerTO;
import com.linkapital.core.services.authorization.contract.to.authorization.ClientAuthorizationDataTO;
import com.linkapital.core.services.authorization.contract.to.authorization.InitOwnerAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.authorization.OwnerAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;
import com.linkapital.core.services.authorization.contract.to.trace.CreateAuthorizationTraceTO;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationAnswer;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationTrace;
import com.linkapital.core.services.authorization.datasource.domain.OwnerAuthorization;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.notification.contract.to.AuthorizationNotificationTO;
import com.linkapital.core.util.functions.TriFunction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.ACCEPTED;
import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.REJECTED;
import static com.linkapital.core.services.security.contract.enums.Authority.ENTREPRENEUR;

@Mapper
public interface OwnerAuthorizationBinder {

    OwnerAuthorizationBinder OWNER_AUTHORIZATION_BINDER = Mappers.getMapper(OwnerAuthorizationBinder.class);

    Function<OwnerAuthorization, AuthorizationNotificationTO> buildAuthorizationNotification = source ->
            new AuthorizationNotificationTO()
                    .withCnpj(source.getCompanyUser().getCompany().getMainInfo().getCnpj())
                    .withAuthorizationId(source.getId())
                    .withAuthorizationState(source.getState());

    TriFunction<Integer, AuthorizationAnswerForAgentTO, OwnerAuthorization, OwnerAuthorization> setState =
            (questions, to, authorization) -> {
                var answers = authorization.getAnswers();
                var allTrue = answers
                        .stream()
                        .allMatch(AuthorizationAnswer::isYesAnswer);
                var user = authorization.getCompanyUser().getUser();
                var authority = user.getRole().getAuthority();

                if (!authority.equals(ENTREPRENEUR)
                        ? allTrue
                        && to.isConsultScr()
                        && to.isHasPower()
                        && to.isHasRepresentativePower()
                        && to.isBelongsCompany()
                        && questions == answers.size()
                        : to.isConsultScr() && to.isHasPower())
                    authorization.setState(ACCEPTED);
                else
                    authorization.setState(REJECTED);

                return authorization
                        .withConsultScr(to.isConsultScr())
                        .withHasPower(to.isHasPower())
                        .withHasRepresentativePower(to.isHasRepresentativePower())
                        .withBelongsCompany(to.isBelongsCompany())
                        .withTrace(OWNER_AUTHORIZATION_BINDER.bind(to.getAuthorizationTrace()));
            };

    BiFunction<AuthorizationAnswerForOwnerTO, CompanyUser, OwnerAuthorization> build = (source, companyUser) ->
            new OwnerAuthorization()
                    .withConsultScr(source.isConsultScr())
                    .withHasPower(source.isHasPower())
                    .withTrace(OWNER_AUTHORIZATION_BINDER.bind(source.getAuthorizationTrace()))
                    .withCompanyUser(companyUser)
                    .withState(source.isConsultScr() && source.isHasPower()
                            ? ACCEPTED
                            : REJECTED);

    OwnerAuthorizationTO bind(OwnerAuthorization source);

    ClientAuthorizationDataTO bindToClient(OwnerAuthorization source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    OwnerAuthorization bind(InitOwnerAuthorizationTO source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    AuthorizationTrace bind(CreateAuthorizationTraceTO source);

    default ClientAuthorizationDataTO bind(OwnerAuthorization source, List<AuthorizationQuestionTO> questions) {
        var mainInfo = source.getCompanyUser().getCompany().getMainInfo();
        var user = source.getCompanyUser().getUser();

        return bindToClient(source)
                .withAgentCpf(user.getCpf())
                .withAgentName(user.getName())
                .withCnpj(mainInfo.getCnpj())
                .withSocialReason(mainInfo.getSocialReason())
                .withQuestions(questions);
    }

}
