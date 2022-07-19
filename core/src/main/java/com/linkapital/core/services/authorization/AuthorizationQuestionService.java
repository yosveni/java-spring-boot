package com.linkapital.core.services.authorization;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.authorization.contract.to.question.AuthorizationQuestionTO;
import com.linkapital.core.services.authorization.contract.to.question.CreateAuthorizationQuestionTO;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationQuestion;

import java.util.List;

/**
 * Default interface for {@link AuthorizationQuestionService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link AuthorizationQuestion}
 *
 * @since 0.0.1
 */
public interface AuthorizationQuestionService {

    /**
     * Save authorization question
     *
     * @param authorizationQuestion {@link AuthorizationQuestion} the authorization question to be register
     * @return {@link AuthorizationQuestion} a created authorization question
     */
    AuthorizationQuestion save(AuthorizationQuestion authorizationQuestion);

    /**
     * Create authorization question.
     *
     * @param to {@link CreateAuthorizationQuestionTO} the authorization question create
     * @return {@link AuthorizationQuestionTO} the authorization question
     * @throws UnprocessableEntityException if not found a bank by id or offer by id
     */
    AuthorizationQuestionTO create(CreateAuthorizationQuestionTO to) throws UnprocessableEntityException;

    /**
     * Update authorization question.
     *
     * @param to {@link AuthorizationQuestionTO} the authorization question update
     * @return {@link AuthorizationQuestionTO} the authorization question
     * @throws UnprocessableEntityException in not found bak by id
     */
    AuthorizationQuestionTO update(AuthorizationQuestionTO to) throws UnprocessableEntityException;

    /**
     * Get authorization question by id.
     *
     * @param id {@link Long} the id
     * @return {@link AuthorizationQuestion} the authorization question
     * @throws UnprocessableEntityException if not found a authorization question by the id
     */
    AuthorizationQuestion getById(Long id) throws UnprocessableEntityException;

    /**
     * Get all authorization question
     *
     * @return {@link List}<{@link AuthorizationQuestionTO}> the authorization question list
     */
    List<AuthorizationQuestionTO> getAll();

    /**
     * Delete authorization question by id
     *
     * @param id {@link Long} the id
     * @throws UnprocessableEntityException if not found a authorization question by id
     */
    void delete(Long id) throws UnprocessableEntityException;

}
