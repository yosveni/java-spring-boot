package com.linkapital.core.services.notification.impl;

import com.linkapital.core.exceptions.SMSException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.notification.EmailService;
import com.linkapital.core.services.notification.NotificationService;
import com.linkapital.core.services.notification.SMSService;
import com.linkapital.core.services.security.contract.enums.NotificationType;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.notification.contract.enums.EmailType.NORMAL;
import static com.linkapital.core.services.security.contract.enums.NotificationType.EMAIL;
import static io.jsonwebtoken.lang.Strings.hasText;
import static java.lang.String.format;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final EmailService emailService;
    private final SMSService smsService;

    public NotificationServiceImpl(EmailService emailService,
                                   SMSService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @Override
    public String sendAndGetCode(String email, String phone, NotificationType type) throws UnprocessableEntityException {
        var code = format("%06d", new Random().nextInt(1000000));

        if (type.equals(EMAIL))
            emailService.send(NORMAL, email, msg("notification.email.registration.code.subject"),
                    msg("notification.email.registration.code.body", code));
        else
            try {
                validatePhoneNotEmpty(phone);
                smsService.send(phone, msg("notification.phone.verification.code", code));
            } catch (SMSException e) {
                throw new UnprocessableEntityException(msg("notification.phone.error"));
            }

        return code;
    }

    //region Validate if the email or phone number exists, according to the type of notification passed by parameter
    private void validatePhoneNotEmpty(String phone) throws UnprocessableEntityException {
        if (!hasText(phone))
            throw new UnprocessableEntityException(msg("notification.phone.not.found"));
    }
    //endregion

}
