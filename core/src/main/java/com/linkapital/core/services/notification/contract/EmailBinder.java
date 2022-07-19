package com.linkapital.core.services.notification.contract;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.mail.SimpleMailMessage;

@Mapper
public interface EmailBinder {

    EmailBinder EMAIL_BINDER = Mappers.getMapper(EmailBinder.class);

    default SimpleMailMessage build(String to, String subject, String text) {
        var message = new SimpleMailMessage();
        message.setFrom("LINKAPITAL");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        return message;
    }

}
