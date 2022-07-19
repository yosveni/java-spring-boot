package com.linkapital.core.services.authorization.contract;

import com.linkapital.core.services.authorization.contract.to.answer.AuthorizationAnswerTO;
import com.linkapital.core.services.authorization.contract.to.authorization.ClientAuthorizationDataTO;
import com.linkapital.core.services.authorization.contract.to.authorization.InitOwnerAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.authorization.OwnerAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;
import com.linkapital.core.services.authorization.contract.to.trace.AuthorizationTraceTO;
import com.linkapital.core.services.authorization.contract.to.trace.CreateAuthorizationTraceTO;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationAnswer;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationQuestion;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationTrace;
import com.linkapital.core.services.authorization.datasource.domain.OwnerAuthorization;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T22:29:28-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class OwnerAuthorizationBinderImpl implements OwnerAuthorizationBinder {

    @Override
    public OwnerAuthorizationTO bind(OwnerAuthorization source) {
        if ( source == null ) {
            return null;
        }

        OwnerAuthorizationTO ownerAuthorizationTO = new OwnerAuthorizationTO();

        ownerAuthorizationTO.setName( source.getName() );
        ownerAuthorizationTO.setEmail( source.getEmail() );
        ownerAuthorizationTO.setCpf( source.getCpf() );
        if ( source.getId() != null ) {
            ownerAuthorizationTO.setId( source.getId() );
        }
        ownerAuthorizationTO.setToken( source.getToken() );
        ownerAuthorizationTO.setCancelledReason( source.getCancelledReason() );
        ownerAuthorizationTO.setConsultScr( source.isConsultScr() );
        ownerAuthorizationTO.setHasPower( source.isHasPower() );
        ownerAuthorizationTO.setState( source.getState() );
        ownerAuthorizationTO.setAuthorizationTrace( authorizationTraceToAuthorizationTraceTO( source.getAuthorizationTrace() ) );
        ownerAuthorizationTO.setAnswers( authorizationAnswerListToAuthorizationAnswerTOList( source.getAnswers() ) );

        return ownerAuthorizationTO;
    }

    @Override
    public ClientAuthorizationDataTO bindToClient(OwnerAuthorization source) {
        if ( source == null ) {
            return null;
        }

        ClientAuthorizationDataTO clientAuthorizationDataTO = new ClientAuthorizationDataTO();

        clientAuthorizationDataTO.setName( source.getName() );
        clientAuthorizationDataTO.setEmail( source.getEmail() );
        clientAuthorizationDataTO.setCpf( source.getCpf() );
        clientAuthorizationDataTO.setToken( source.getToken() );
        clientAuthorizationDataTO.setState( source.getState() );

        return clientAuthorizationDataTO;
    }

    @Override
    public OwnerAuthorization bind(InitOwnerAuthorizationTO source) {
        if ( source == null ) {
            return null;
        }

        OwnerAuthorization ownerAuthorization = new OwnerAuthorization();

        ownerAuthorization.setName( source.getName() );
        ownerAuthorization.setEmail( source.getEmail() );
        ownerAuthorization.setCpf( source.getCpf() );

        return ownerAuthorization;
    }

    @Override
    public AuthorizationTrace bind(CreateAuthorizationTraceTO source) {
        if ( source == null ) {
            return null;
        }

        AuthorizationTrace authorizationTrace = new AuthorizationTrace();

        authorizationTrace.setIp( source.getIp() );
        authorizationTrace.setCity( source.getCity() );
        authorizationTrace.setCountryName( source.getCountryName() );
        authorizationTrace.setCep( source.getCep() );
        authorizationTrace.setNavigator( source.getNavigator() );
        authorizationTrace.setDevice( source.getDevice() );
        authorizationTrace.setLatitude( source.getLatitude() );
        authorizationTrace.setLongitude( source.getLongitude() );

        return authorizationTrace;
    }

    protected AuthorizationTraceTO authorizationTraceToAuthorizationTraceTO(AuthorizationTrace authorizationTrace) {
        if ( authorizationTrace == null ) {
            return null;
        }

        AuthorizationTraceTO authorizationTraceTO = new AuthorizationTraceTO();

        if ( authorizationTrace.getId() != null ) {
            authorizationTraceTO.setId( authorizationTrace.getId() );
        }
        authorizationTraceTO.setIp( authorizationTrace.getIp() );
        authorizationTraceTO.setCity( authorizationTrace.getCity() );
        authorizationTraceTO.setCountryName( authorizationTrace.getCountryName() );
        authorizationTraceTO.setCep( authorizationTrace.getCep() );
        authorizationTraceTO.setNavigator( authorizationTrace.getNavigator() );
        authorizationTraceTO.setDevice( authorizationTrace.getDevice() );
        authorizationTraceTO.setLatitude( authorizationTrace.getLatitude() );
        authorizationTraceTO.setLongitude( authorizationTrace.getLongitude() );

        return authorizationTraceTO;
    }

    protected AuthorizationQuestionTO authorizationQuestionToAuthorizationQuestionTO(AuthorizationQuestion authorizationQuestion) {
        if ( authorizationQuestion == null ) {
            return null;
        }

        AuthorizationQuestionTO authorizationQuestionTO = new AuthorizationQuestionTO();

        authorizationQuestionTO.setQuestion( authorizationQuestion.getQuestion() );
        authorizationQuestionTO.setDetailTitle( authorizationQuestion.getDetailTitle() );
        authorizationQuestionTO.setDetail( authorizationQuestion.getDetail() );
        if ( authorizationQuestion.getId() != null ) {
            authorizationQuestionTO.setId( authorizationQuestion.getId() );
        }

        return authorizationQuestionTO;
    }

    protected AuthorizationAnswerTO authorizationAnswerToAuthorizationAnswerTO(AuthorizationAnswer authorizationAnswer) {
        if ( authorizationAnswer == null ) {
            return null;
        }

        AuthorizationAnswerTO authorizationAnswerTO = new AuthorizationAnswerTO();

        if ( authorizationAnswer.getId() != null ) {
            authorizationAnswerTO.setId( authorizationAnswer.getId() );
        }
        authorizationAnswerTO.setYesAnswer( authorizationAnswer.isYesAnswer() );
        authorizationAnswerTO.setQuestion( authorizationQuestionToAuthorizationQuestionTO( authorizationAnswer.getQuestion() ) );

        return authorizationAnswerTO;
    }

    protected List<AuthorizationAnswerTO> authorizationAnswerListToAuthorizationAnswerTOList(List<AuthorizationAnswer> list) {
        if ( list == null ) {
            return null;
        }

        List<AuthorizationAnswerTO> list1 = new ArrayList<AuthorizationAnswerTO>( list.size() );
        for ( AuthorizationAnswer authorizationAnswer : list ) {
            list1.add( authorizationAnswerToAuthorizationAnswerTO( authorizationAnswer ) );
        }

        return list1;
    }
}
