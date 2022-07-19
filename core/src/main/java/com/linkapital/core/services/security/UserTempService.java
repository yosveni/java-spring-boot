package com.linkapital.core.services.security;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.security.contract.to.CodeNotificationTO;
import com.linkapital.core.services.security.contract.to.ConfirmationCodeTO;
import com.linkapital.core.services.security.contract.to.RegisterUserTO;
import com.linkapital.core.services.security.contract.to.UserAuthenticatedTO;
import com.linkapital.core.services.security.contract.to.UserTempTO;
import com.linkapital.core.services.security.datasource.domain.UserTemp;

import java.util.List;

/**
 * Default interface for {@link UserTempService}
 * Has the responsibility to make operations over the {@link UserTemp} entity
 *
 * @since 0.0.1
 */
public interface UserTempService {

    /**
     * Register a new user.
     *
     * @param to {@link RegisterUserTO} the user temp to register
     * @return {@link UserTempTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    UserTempTO register(RegisterUserTO to) throws UnprocessableEntityException;

    /**
     * Confirmation code user registered to.
     *
     * @param to {@link ConfirmationCodeTO} the email user and code to confirm.
     * @return {@link UserAuthenticatedTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    UserAuthenticatedTO registerConfirmationCode(ConfirmationCodeTO to) throws UnprocessableEntityException;

    /**
     * Send code to email.
     *
     * @param to {@link CodeNotificationTO} the data to send the notification code.
     * @return {@link UserTempTO}
     * @throws UnprocessableEntityException if any error occurs
     */
    UserTempTO sendCode(CodeNotificationTO to) throws UnprocessableEntityException;

    /**
     * Gets all the user temp.
     *
     * @return {@link List}<{@link UserTempTO}>  all temporary users
     */
    List<UserTempTO> getAll();

    /**
     * Delete the temporary user by id.
     *
     * @param id {@link Long} the temporary user id
     */
    void delete(long id) throws UnprocessableEntityException;

}
