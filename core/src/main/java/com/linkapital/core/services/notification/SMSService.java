package com.linkapital.core.services.notification;

import com.linkapital.core.exceptions.SMSException;

/**
 * Default interface for {@link SMSService}\
 * <p>
 * Service with the responsibility of carrying out SMS sending operations
 *
 * @since 0.0.1
 */
public interface SMSService {

    /**
     * Send the sms to the recipient number with the message passed by parameter
     *
     * @param number  {@link String} the telephone number
     * @param message {@link String} the message to send
     * @throws SMSException if an error occurs when sending the message
     */
    void send(String number, String message) throws SMSException;

}
