package com.linkapital.core.services.whatsapp;

import com.linkapital.core.exceptions.NotFoundSystemConfigurationException;
import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.whatsapp.contract.to.CreateUserWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.DefaultMessageWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UpdateMessageWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UpdateUserWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UserWhatsAppTO;
import com.linkapital.core.services.whatsapp.datasource.domain.UserWhatsApp;

import java.util.List;

public interface WhatsAppService {

    /**
     * Create a whatsapp user
     *
     * @param to {@link CreateUserWhatsAppTO} the user to create
     * @return {@link UserWhatsAppTO} the user created
     * @throws UnprocessableEntityException if you try to create a user with a number that already exists
     */
    UserWhatsAppTO save(CreateUserWhatsAppTO to) throws UnprocessableEntityException;

    /**
     * Update a whatsapp user
     *
     * @param to {@link UpdateUserWhatsAppTO} the user to update
     * @return {@link UserWhatsAppTO} the user updated
     * @throws ResourceNotFoundException    the user cannot be found by id
     * @throws UnprocessableEntityException if you try to create a user with a number that already exists
     */
    UserWhatsAppTO update(UpdateUserWhatsAppTO to) throws ResourceNotFoundException, UnprocessableEntityException;

    /**
     * Update the default whatsapp message.
     *
     * @param to {@link UpdateMessageWhatsAppTO} the update message whatsapp to
     * @return {@link DefaultMessageWhatsAppTO} the new message
     * @throws NotFoundSystemConfigurationException if the whatsapp configuration does not exist in the configuration table.
     */
    DefaultMessageWhatsAppTO updateDefaultMessage(UpdateMessageWhatsAppTO to) throws NotFoundSystemConfigurationException;

    /**
     * Get a whatsapp user by id
     *
     * @param id {@link Long} the user id
     * @return {@link UserWhatsAppTO} the {@link UserWhatsApp}
     * @throws ResourceNotFoundException the user cannot be found by id
     */
    UserWhatsApp getById(Long id) throws ResourceNotFoundException;

    /**
     * Get the default whatsapp message.
     *
     * @return {@link DefaultMessageWhatsAppTO} the default whatsapp to
     * @throws NotFoundSystemConfigurationException if the whatsapp configuration does not exist in the configuration table.
     */
    DefaultMessageWhatsAppTO getDefaultMessage() throws NotFoundSystemConfigurationException;

    /**
     * Get all whatsapp user
     *
     * @return {@link List}<{@link UserWhatsAppTO}>
     */
    List<UserWhatsAppTO> getAll();

    /**
     * Get a random whatsapp user without repeating them until all have been called
     *
     * @return {@link UserWhatsApp} the user whatsapp
     * @throws UnprocessableEntityException if Couldn't create strong secure random generator
     */
    UserWhatsAppTO getRandomUser() throws UnprocessableEntityException;

    /**
     * Delete a whatsapp user by id
     *
     * @param id {@link Long} the user id
     * @throws ResourceNotFoundException the user cannot be found by id
     */
    void delete(Long id) throws ResourceNotFoundException;

}
