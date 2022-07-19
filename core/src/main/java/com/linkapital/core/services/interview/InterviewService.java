package com.linkapital.core.services.interview;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.interview.contract.to.AnswerQuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.CreateQuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.QuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.UpdateQuestionInterviewTO;
import com.linkapital.core.services.interview.datasource.domain.QuestionInterview;

import java.util.List;

/**
 * The interface Interview service.
 */
public interface InterviewService {

    /**
     * Save question interview.
     *
     * @param to {@link CreateQuestionInterviewTO} the question interview to create
     * @return {@link QuestionInterviewTO} the question interview
     */
    QuestionInterviewTO save(CreateQuestionInterviewTO to);

    /**
     * Update question interview.
     *
     * @param to {@link UpdateQuestionInterviewTO} the question interview to update
     * @return {@link QuestionInterviewTO} the question interview
     * @throws UnprocessableEntityException if not found a {@link QuestionInterview} entity in datasource by the given id
     */
    QuestionInterviewTO update(UpdateQuestionInterviewTO to) throws UnprocessableEntityException;

    /**
     * Get question interview by id.
     *
     * @param id {@link Long} the question interview id
     * @return {@link QuestionInterview} the question interview
     * @throws UnprocessableEntityException if not found a {@link QuestionInterview} entity in datasource by the given id
     */
    QuestionInterview getById(long id) throws UnprocessableEntityException;

    /**
     * Get question interview by id.
     *
     * @param id {@link Long} the question interview id
     * @return {@link QuestionInterviewTO} the question interview
     * @throws UnprocessableEntityException if not found a {@link QuestionInterview} entity in datasource by the given id
     */
    QuestionInterviewTO getQuestionInterviewById(long id) throws UnprocessableEntityException;

    /**
     * Ge all list.
     *
     * @return the list of question interview
     */
    List<QuestionInterviewTO> geAll();

    /**
     * Associate answer interviews with a company user
     *
     * @param to {@link AnswerQuestionInterviewTO} the answer interview
     * @throws UnprocessableEntityException if not found a {@link QuestionInterview} entity in datasource by the given id
     */
    void answerQuestions(AnswerQuestionInterviewTO to) throws UnprocessableEntityException;

    /**
     * Delete question interview.
     *
     * @param id {@link Long} the question interview id
     */
    void delete(long id) throws UnprocessableEntityException;

}
