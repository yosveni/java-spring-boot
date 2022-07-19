package com.linkapital.core.services.interview.contract;

import com.linkapital.core.services.interview.contract.to.CreateQuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.QuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.UpdateQuestionInterviewTO;
import com.linkapital.core.services.interview.datasource.domain.QuestionInterview;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InterviewBinder {

    InterviewBinder INTERVIEW_BINDER = Mappers.getMapper(InterviewBinder.class);

    QuestionInterview bind(CreateQuestionInterviewTO source);

    QuestionInterviewTO bind(QuestionInterview source);

    QuestionInterview set(@MappingTarget QuestionInterview target, UpdateQuestionInterviewTO source);

    List<QuestionInterviewTO> bind(List<QuestionInterview> source);

}
