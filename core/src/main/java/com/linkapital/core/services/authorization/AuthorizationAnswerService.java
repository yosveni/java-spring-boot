package com.linkapital.core.services.authorization;

import com.linkapital.core.services.authorization.datasource.domain.AuthorizationAnswer;
import com.linkapital.core.services.authorization.datasource.domain.AuthorizationQuestion;

/**
 * Default interface for {@link AuthorizationAnswerService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link AuthorizationAnswer}
 *
 * @since 0.0.1
 */
public interface AuthorizationAnswerService {

    /**
     * Delete all {@link AuthorizationAnswer } answers to one {@link AuthorizationAnswer}
     *
     * @param authorizationQuestion the {@link AuthorizationQuestion} to delete their {@link AuthorizationAnswer}
     */
    void deleteAllByAuthorizationQuestion(AuthorizationQuestion authorizationQuestion);

}
