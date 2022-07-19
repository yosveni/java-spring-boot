package com.linkapital.core.services.interview.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.CompanyUserService;
import com.linkapital.core.services.interview.InterviewService;
import com.linkapital.core.services.interview.contract.to.AnswerQuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.CreateAnswerInterviewTO;
import com.linkapital.core.services.interview.contract.to.CreateQuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.QuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.UpdateQuestionInterviewTO;
import com.linkapital.core.services.interview.datasource.QuestionInterviewRepository;
import com.linkapital.core.services.interview.datasource.domain.AnswerInterview;
import com.linkapital.core.services.interview.datasource.domain.QuestionInterview;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.interview.contract.InterviewBinder.INTERVIEW_BINDER;

@Service
public class InterviewServiceImpl implements InterviewService {

    private final QuestionInterviewRepository questionInterviewRepository;
    private final CompanyUserService companyUserService;

    public InterviewServiceImpl(QuestionInterviewRepository questionInterviewRepository,
                                CompanyUserService companyUserService) {
        this.questionInterviewRepository = questionInterviewRepository;
        this.companyUserService = companyUserService;
    }

    @Override
    public QuestionInterviewTO save(CreateQuestionInterviewTO to) {
        return INTERVIEW_BINDER.bind(save(INTERVIEW_BINDER.bind(to)));
    }

    @Override
    public QuestionInterviewTO update(UpdateQuestionInterviewTO to) throws UnprocessableEntityException {
        return INTERVIEW_BINDER.bind(save(INTERVIEW_BINDER.set(getById(to.getId()), to)));
    }

    @Override
    public QuestionInterview getById(long id) throws UnprocessableEntityException {
        return questionInterviewRepository.findById(id).orElseThrow(() ->
                new UnprocessableEntityException(msg("question.interview.not.found", id)));
    }

    @Override
    public QuestionInterviewTO getQuestionInterviewById(long id) throws UnprocessableEntityException {
        return INTERVIEW_BINDER.bind(getById(id));
    }

    @Override
    public List<QuestionInterviewTO> geAll() {
        return INTERVIEW_BINDER.bind(questionInterviewRepository.findAll());
    }

    @Override
    public void answerQuestions(AnswerQuestionInterviewTO to) throws UnprocessableEntityException {
        var companyUser = companyUserService.get(to.getCnpj(), to.getUserId());

        for (CreateAnswerInterviewTO aq : to.getAnswers()) {
            var question = getById(aq.getQuestionId());
            var answer = new AnswerInterview()
                    .withAnswer(aq.getAnswerValue())
                    .withQuestionInterview(question);
            companyUser.getAnswerInterviews().add(answer);
        }

        companyUserService.save(companyUser);
    }

    @Override
    public void delete(long id) throws UnprocessableEntityException {
        questionInterviewRepository.delete(getById(id));
    }

    private @NonNull QuestionInterview save(QuestionInterview to) {
        return questionInterviewRepository.saveAndFlush(to);
    }

}
