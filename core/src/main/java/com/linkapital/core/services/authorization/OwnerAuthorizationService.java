package com.linkapital.core.services.authorization;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.authorization.contract.to.answer.AuthorizationAnswerForAgentTO;
import com.linkapital.core.services.authorization.contract.to.answer.AuthorizationAnswerForOwnerTO;
import com.linkapital.core.services.authorization.contract.to.authorization.CancelAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.authorization.ClientAuthorizationDataTO;
import com.linkapital.core.services.authorization.contract.to.authorization.InitOwnerAuthorizationTO;
import com.linkapital.core.services.authorization.contract.to.authorization.OwnerAuthorizationTO;
import com.linkapital.core.services.authorization.datasource.domain.OwnerAuthorization;
import lombok.NonNull;

/**
 * Default interface for {@link OwnerAuthorizationService}
 * Service with the responsibility of carrying out operations and business logic on the entity {@link OwnerAuthorization}
 *
 * @since 0.0.1
 */
public interface OwnerAuthorizationService {

    /**
     * Answer authorization questions
     *
     * @param to {@link AuthorizationAnswerForAgentTO} the owner authorization data
     * @return {@link OwnerAuthorizationTO}
     * @throws UnprocessableEntityException if an error occurred
     */
    OwnerAuthorizationTO answerQuestions(@NonNull AuthorizationAnswerForAgentTO to) throws UnprocessableEntityException;

    /**
     * Get owner authorization by id.
     *
     * @param id {@link Long} the id
     * @return {@link OwnerAuthorization} the owner authorization
     * @throws UnprocessableEntityException if not found owner authorization by the id
     */
    OwnerAuthorization getById(long id) throws UnprocessableEntityException;

    /**
     * Gets owner authorization by company cnpj.
     *
     * @param cnpj   {@link String}  the company cnpj
     * @param userId {@link Long}  the company cnpj
     * @return {@link OwnerAuthorizationTO} the owner authorization
     */
    OwnerAuthorizationTO getByCompanyUser(String cnpj, long userId) throws UnprocessableEntityException;

    /**
     * Get authorization data for the owner.
     *
     * @param token {@link String}  the owner authorization token
     * @return {@link ClientAuthorizationDataTO} the authorization data
     */
    ClientAuthorizationDataTO getForClient(String token) throws UnprocessableEntityException;

    /**
     * Init authorization for agent.
     *
     * @param to {@link InitOwnerAuthorizationTO} the data to start the authorization
     * @throws UnprocessableEntityException if an error occurred
     */
    void initForAgent(InitOwnerAuthorizationTO to) throws UnprocessableEntityException;

    /**
     * Init authorization for owner.
     *
     * @param to {@link AuthorizationAnswerForOwnerTO} the data to start the authorization
     * @throws UnprocessableEntityException if an error occurred
     */
    void initForOwner(AuthorizationAnswerForOwnerTO to) throws UnprocessableEntityException;

    /**
     * Cancel owner authorization
     *
     * @param to {@link CancelAuthorizationTO} the data to cancel the authorization
     * @throws UnprocessableEntityException if an error occurred
     */
    void cancel(CancelAuthorizationTO to) throws UnprocessableEntityException;

    /**
     * Delete owner authorization by id
     *
     * @param id {@link Long} the id
     * @throws UnprocessableEntityException if not found owner authorization by the id
     */
    void delete(long id) throws UnprocessableEntityException;

}
