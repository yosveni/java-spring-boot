package com.linkapital.core.services.interview.contract;

import com.linkapital.core.services.interview.contract.to.CreateQuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.QuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.UpdateQuestionInterviewTO;
import com.linkapital.core.services.interview.datasource.domain.QuestionInterview;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class InterviewBinderImpl implements InterviewBinder {

    @Override
    public QuestionInterview bind(CreateQuestionInterviewTO source) {
        if ( source == null ) {
            return null;
        }

        QuestionInterview questionInterview = new QuestionInterview();

        questionInterview.setQuestion( source.getQuestion() );
        questionInterview.setPossibleValue( source.getPossibleValue() );
        questionInterview.setArea( source.getArea() );
        questionInterview.setField( source.getField() );

        return questionInterview;
    }

    @Override
    public QuestionInterviewTO bind(QuestionInterview source) {
        if ( source == null ) {
            return null;
        }

        QuestionInterviewTO questionInterviewTO = new QuestionInterviewTO();

        questionInterviewTO.setQuestion( source.getQuestion() );
        questionInterviewTO.setPossibleValue( source.getPossibleValue() );
        questionInterviewTO.setArea( source.getArea() );
        questionInterviewTO.setField( source.getField() );
        if ( source.getId() != null ) {
            questionInterviewTO.setId( source.getId() );
        }
        questionInterviewTO.setCreated( source.getCreated() );

        return questionInterviewTO;
    }

    @Override
    public QuestionInterview set(QuestionInterview target, UpdateQuestionInterviewTO source) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setQuestion( source.getQuestion() );
        target.setPossibleValue( source.getPossibleValue() );
        target.setArea( source.getArea() );
        target.setField( source.getField() );

        return target;
    }

    @Override
    public List<QuestionInterviewTO> bind(List<QuestionInterview> source) {
        if ( source == null ) {
            return null;
        }

        List<QuestionInterviewTO> list = new ArrayList<QuestionInterviewTO>( source.size() );
        for ( QuestionInterview questionInterview : source ) {
            list.add( bind( questionInterview ) );
        }

        return list;
    }
}
