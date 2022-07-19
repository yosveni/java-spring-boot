package com.linkapital.core.services.authorization.contract;

import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;
import com.linkapital.core.services.authorization.contract.to.question.CreateAuthorizationQuestionTO;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationQuestion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class AuthorizationQuestionBinderImpl implements AuthorizationQuestionBinder {

    @Override
    public AuthorizationQuestionTO bind(AuthorizationQuestion source) {
        if ( source == null ) {
            return null;
        }

        AuthorizationQuestionTO authorizationQuestionTO = new AuthorizationQuestionTO();

        authorizationQuestionTO.setQuestion( source.getQuestion() );
        authorizationQuestionTO.setDetailTitle( source.getDetailTitle() );
        authorizationQuestionTO.setDetail( source.getDetail() );
        if ( source.getId() != null ) {
            authorizationQuestionTO.setId( source.getId() );
        }

        return authorizationQuestionTO;
    }

    @Override
    public AuthorizationQuestion bind(CreateAuthorizationQuestionTO source) {
        if ( source == null ) {
            return null;
        }

        AuthorizationQuestion authorizationQuestion = new AuthorizationQuestion();

        authorizationQuestion.setQuestion( source.getQuestion() );
        authorizationQuestion.setDetail( source.getDetail() );
        authorizationQuestion.setDetailTitle( source.getDetailTitle() );

        return authorizationQuestion;
    }

    @Override
    public AuthorizationQuestion bind(AuthorizationQuestionTO source) {
        if ( source == null ) {
            return null;
        }

        AuthorizationQuestion authorizationQuestion = new AuthorizationQuestion();

        authorizationQuestion.setId( source.getId() );
        authorizationQuestion.setQuestion( source.getQuestion() );
        authorizationQuestion.setDetail( source.getDetail() );
        authorizationQuestion.setDetailTitle( source.getDetailTitle() );

        return authorizationQuestion;
    }

    @Override
    public AuthorizationQuestion set(AuthorizationQuestion target, AuthorizationQuestionTO source) {
        if ( source == null ) {
            return null;
        }

        target.setQuestion( source.getQuestion() );
        target.setDetail( source.getDetail() );
        target.setDetailTitle( source.getDetailTitle() );

        return target;
    }

    @Override
    public List<AuthorizationQuestionTO> bindToListTO(List<AuthorizationQuestion> source) {
        if ( source == null ) {
            return null;
        }

        List<AuthorizationQuestionTO> list = new ArrayList<AuthorizationQuestionTO>( source.size() );
        for ( AuthorizationQuestion authorizationQuestion : source ) {
            list.add( bind( authorizationQuestion ) );
        }

        return list;
    }
}
