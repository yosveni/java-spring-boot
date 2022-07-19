package com.linkapital.core.services.notification;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.security.contract.enums.NotificationType;

/**
 * Default interface for {@link NotificationService}\
 * <p>
 * Service with the responsibility of carrying out the operations of sending email and sms
 *
 * @since 0.0.1
 */
public interface NotificationService {

    /**
     * Send a confirmation code by email or sms according to the parameter {@link NotificationType}
     *
     * @param email {@link String} the user email
     * @param phone {@link String} the data to send the notification code
     * @param type  {@link NotificationType} the notification type
     * @return {@link String} the code generated and sent
     * @throws UnprocessableEntityException if an error occurs when sending the message
     */
    String sendAndGetCode(String email, String phone, NotificationType type) throws UnprocessableEntityException;

}
