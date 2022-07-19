package com.linkapital.core.services.notification;

import com.linkapital.core.services.notification.contract.enums.WebsocketBroker;
import com.linkapital.core.services.security.contract.enums.Code;
import com.linkapital.core.services.security.datasource.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.singleton;

/**
 * Service in charge of Websockets responses
 */
public interface WebsocketService {

    String OFFER_NOTIFICATION = "offer_notification";
    String OFFER_STATUS_CHANGED = "offer_status_changed";
    String COMMENT_CREATED = "comment_created";
    String AUTHORIZATION_STATUS_CHANGED = "authorization_status_changed";
    String INSTALLMENT_STATUS_CHANGED = "installment_status_changed";
    String INSTALLMENT_PAYMENT_TICKET_UPLOADED = "installment_payment_ticket_uploaded";
    String BANK_DOCUMENT_STATUS_CHANGED = "bank_document_status_changed";

    /**
     * Send Websocket event to all sessions opened by any of the provided users
     *
     * @param recipientsEmails {@link Collection} <{@link String}> A collection of emails from users to notify
     * @param prefix           {@link String} the websockets broker prefix, to be added before destination
     * @param destination      {@link String} the websockets endpoint to where the response will be sent
     * @param message          {@link String} message attached to response.
     * @param payload          {@link Object} extra data
     */
    void dispatch(Collection<String> recipientsEmails, WebsocketBroker prefix, String destination, String message,
                  Object payload);

    /**
     * Send Websocket event to all sessions opened by the provided user
     *
     * @param prefix      {@link String} the email of the user to notify
     * @param destination {@link String} the websockets endpoint to where the response will be sent
     * @param message     {@link String} message attached to response.
     * @param payload     {@link Object} extra data
     */
    default void dispatch(String recipient, WebsocketBroker prefix, String destination, String message, Object payload) {
        dispatch(singleton(recipient), prefix, destination, message, payload);
    }

    /**
     * Send Websocket event to all sessions opened by the current logged user
     *
     * @param prefix      {@link String} the websockets broker prefix, to be added before destination
     * @param destination {@link String} the websockets endpoint to where the response will be sent
     * @param message     {@link String} message attached to response.
     * @param payload     {@link Object} extra data
     */
    default void dispatch(WebsocketBroker prefix, String destination, String message, Object payload) {
        var recipient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dispatch(recipient.getEmail(), prefix, destination, message, payload);
    }

    /**
     * Send Websocket event to all sessions opened by an user with the provided Role Code
     *
     * @param codes       {@link List}<{@link Code}> the list of role code of users which the socket is targeted
     * @param prefix      {@link String} the websockets broker prefix, to be added before destination
     * @param destination {@link String} the websockets endpoint to where the response will be sent
     * @param message     {@link String} message attached to response.
     * @param payload     {@link Object} extra data
     */
    void dispatch(List<Code> codes, WebsocketBroker prefix, String destination, String message, Object payload);

}
