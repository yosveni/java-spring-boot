package com.linkapital.core.services.security;

import com.linkapital.core.exceptions.InvalidRefreshTokenException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.identification.contract.to.IdentificationTO;
import com.linkapital.core.services.security.contract.enums.Authority;
import com.linkapital.core.services.security.contract.enums.Intensity;
import com.linkapital.core.services.security.contract.to.AuthenticationRequestTO;
import com.linkapital.core.services.security.contract.to.CodeNotificationTO;
import com.linkapital.core.services.security.contract.to.ConfirmationCodeTO;
import com.linkapital.core.services.security.contract.to.RefreshTokenTO;
import com.linkapital.core.services.security.contract.to.ResetPasswordTO;
import com.linkapital.core.services.security.contract.to.UserActiveTO;
import com.linkapital.core.services.security.contract.to.UserAuthenticatedTO;
import com.linkapital.core.services.security.contract.to.UserIdentificationTO;
import com.linkapital.core.services.security.contract.to.UserTO;
import com.linkapital.core.services.security.contract.to.create.CreateUserTO;
import com.linkapital.core.services.security.contract.to.update.UpdatePasswordTO;
import com.linkapital.core.services.security.contract.to.update.UpdateUserTO;
import com.linkapital.core.services.security.datasource.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Default interface for {@link UserService}
 * Service with the responsibility of performing operations on the user entity
 *
 * @since 0.0.1
 */
public interface UserService {

    /**
     * Save a {@link User } entity
     *
     * @param user {@link User} the user to be register
     * @return {@link User}
     */
    User save(User user);

    /**
     * Save the security data for a user, password, roles and other security data associate with the user company
     *
     * @param user {@link CreateUserTO} the user to be saved
     * @return {@link UserTO}
     * @throws UnprocessableEntityException if not exists a user with the given user email
     */
    UserTO create(CreateUserTO user) throws UnprocessableEntityException;

    /**
     * Update a user entity
     *
     * @param to {@link UpdateUserTO} the user to be updated
     * @return {@link User}
     * @throws UnprocessableEntityException if not exists a user with the given user email
     */
    UserTO update(UpdateUserTO to) throws UnprocessableEntityException;

    /**
     * Update a user email
     *
     * @param email {@link String} the user email
     * @return {@link UserAuthenticatedTO}
     */
    UserAuthenticatedTO updateEmail(String email);

    /**
     * Retrieve an user entity from the database
     *
     * @param id {@link Long} the user id
     * @return {@link User}
     * @throws UnprocessableEntityException if not exists a user given the id
     */
    User getById(long id) throws UnprocessableEntityException;

    /**
     * Retrieve a user given an email
     *
     * @param email {@link String} the user email
     * @return {@link User}
     * @throws UnprocessableEntityException if not exists a user given the email
     */
    User getByEmail(String email) throws UnprocessableEntityException;

    /**
     * Enable or disable a user in data base and set the token as expired
     *
     * @param id {@link Long} the user id
     * @return {@link User}
     * @throws UnprocessableEntityException only happens when other user tray to enable/disable the admin account
     */
    UserTO enable(long id) throws UnprocessableEntityException;

    /**
     * Authenticate a user into the system and assigns the user credentials
     *
     * @param to {@link AuthenticationRequestTO} the credentials of the user to authenticate
     * @return {@link UserAuthenticatedTO}
     * @throws UnprocessableEntityException if the user not found or if any error occur assigning retrieve credentials
     *                                      for the given user
     */
    UserAuthenticatedTO login(AuthenticationRequestTO to) throws UnprocessableEntityException;

    /**
     * Get the user identification data
     *
     * @param id {@link Long} the user id
     * @return {@link IdentificationTO}
     * @throws UnprocessableEntityException if not exists a user given the email
     */
    IdentificationTO getIdentificationByUser(long id) throws UnprocessableEntityException;

    /**
     * Change the status of the identification to accepted. The state value changes to FACE
     *
     * @param id {@link Long} the user id
     * @return {@link IdentificationTO}
     * @throws UnprocessableEntityException if not exists a user given the email
     */
    IdentificationTO acceptIdentification(long id) throws UnprocessableEntityException;

    /**
     * Get user profile
     *
     * @return {@link UserTO}
     * @throws UnprocessableEntityException if the user don't exists in database
     */
    UserTO getProfile() throws UnprocessableEntityException;

    /**
     * Refresh a token for a authenticated user
     *
     * @param to {@link RefreshTokenTO} the data to update the token
     * @return {@link RefreshTokenTO}
     * @throws InvalidRefreshTokenException if the refresh token don't correspond with the given token to be refreshed
     */
    RefreshTokenTO refreshToken(RefreshTokenTO to) throws InvalidRefreshTokenException;

    /**
     * Update a password for a given user
     *
     * @param to {@link UpdatePasswordTO} the password data
     * @return {@link UserTO}
     * @throws UnprocessableEntityException if there are errors with the passwords
     */
    UserTO updatePassword(UpdatePasswordTO to) throws UnprocessableEntityException;

    /**
     * Get all the users registered in the database
     *
     * @return {@link List}<{@link UserTO}>
     */
    List<UserTO> getAll();

    /**
     * Get all active users.
     *
     * @return {@link List}<{@link UserActiveTO}>
     */
    List<UserActiveTO> getAllActive();

    /**
     * GReturns a list of users that did not complete the identification or that the process failed
     *
     * @return {@link List}<{@link UserIdentificationTO}>
     */
    List<UserIdentificationTO> getAllWithIdentificationFailed();

    /**
     * Send code to email.
     *
     * @param to {@link CodeNotificationTO} the data to send the notification code
     * @throws UnprocessableEntityException if the user don't exists in database
     */
    UserTO sendCode(CodeNotificationTO to) throws UnprocessableEntityException;

    /**
     * Reset the password of a user client
     *
     * @param to {@link ResetPasswordTO} the data to reset the user's password
     * @throws UnprocessableEntityException if the user can't be found by the given id
     */
    void resetPassword(ResetPasswordTO to) throws UnprocessableEntityException;

    /**
     * Reset the password of a user from administration
     *
     * @param to {@link ResetPasswordTO} the data to reset the user's password
     * @throws UnprocessableEntityException if the user can't be found by the given id
     */
    void resetPasswordFromAdmin(ResetPasswordTO to) throws UnprocessableEntityException;

    /**
     * Confirm the code sent to the user.
     *
     * @param to {@link ConfirmationCodeTO} the data to confirm the code send to user
     * @return {@link UserAuthenticatedTO}
     * @throws UnprocessableEntityException if the user don't exists in database
     */
    UserAuthenticatedTO confirmationCode(ConfirmationCodeTO to) throws UnprocessableEntityException;

    /**
     * Update user intensity.
     *
     * @param intensity {@link Intensity} the intensity
     * @return {@link UserTO}
     * @throws UnprocessableEntityException if {@link User} is not found
     */
    UserTO updateIntensity(Intensity intensity) throws UnprocessableEntityException;

    /**
     * Update user identification by intensity.
     *
     * @param user {@link User} the user data
     * @return {@link User}
     */
    User updateUserIdentificationByIntensity(User user);

    /**
     * Returns the role authority of the authenticated user.
     *
     * @return {@link Authority}
     */
    Authority getAuthority();

    /**
     * Upload image of user.
     *
     * @param file {@link MultipartFile} the file
     * @return {@link UserTO}
     * @throws UnprocessableEntityException if error occurs
     */
    UserTO uploadImage(MultipartFile file) throws UnprocessableEntityException;

    /**
     * Delete a user for the database
     *
     * @param id {@link Long} the id of user to be deleted
     * @throws UnprocessableEntityException if the user don't exists in database
     */
    void delete(long id) throws UnprocessableEntityException;

    /**
     * Save users in bulk process
     *
     * @param users {@link List}<@link User> th user list to be saved
     */
    void saveAll(List<User> users);

    /**
     * Enable all users who made a rate to do it again
     */
    void enableRateForAll();

    /**
     * Delete the relationship between the user and the companies
     *
     * @param email {@link String} the user email
     * @throws UnprocessableEntityException if user is not found
     */
    void deleteCompaniesRelatedToUser(String email) throws UnprocessableEntityException;

}
