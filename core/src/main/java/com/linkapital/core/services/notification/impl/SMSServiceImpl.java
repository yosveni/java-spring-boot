package com.linkapital.core.services.notification.impl;

import com.linkapital.core.exceptions.SMSException;
import com.linkapital.core.services.notification.SMSService;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl implements SMSService {

    private final String sid;
    private final String token;
    private final String number;

    public SMSServiceImpl(@Value("${sms.sid}") String sid,
                          @Value("${sms.token}") String token,
                          @Value("${sms.number}") String number) {
        this.sid = sid;
        this.token = token;
        this.number = number;
    }

    @Override
    public void send(String recipientNumber, String message) throws SMSException {
        try {
            Twilio.init(sid, token);
            Message
                    .creator(new PhoneNumber(recipientNumber), new PhoneNumber(this.number), message)
                    .create();
        } catch (ApiException e) {
            throw new SMSException(e.getMessage());
        }
    }

}
