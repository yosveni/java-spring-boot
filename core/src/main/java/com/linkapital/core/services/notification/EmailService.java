package com.linkapital.core.services.notification;

import com.linkapital.core.services.notification.contract.enums.EmailType;
import org.springframework.scheduling.annotation.Async;

import javax.validation.constraints.NotNull;

/**
 * Default interface for {@link EmailService}
 * Has the responsibility to send an email in the account confirmation process
 *
 * @since 0.0.1
 */
public interface EmailService {

    /**
     * Send a code to a registered user via email
     * this email contains the registration code to complete the registration process
     *
     * @param type   {@link EmailType} the email type
     * @param params {@link Object} allows one or more parameters
     */
    @Async
    void send(EmailType type, @NotNull Object... params);

}
