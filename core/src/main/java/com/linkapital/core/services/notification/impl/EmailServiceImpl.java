package com.linkapital.core.services.notification.impl;

import com.linkapital.core.services.company.contract.to.SelectIndicativeOfferTO;
import com.linkapital.core.services.notification.EmailService;
import com.linkapital.core.services.notification.contract.enums.EmailType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.notification.contract.EmailBinder.EMAIL_BINDER;

/**
 * Has the responsibility to send emails.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final List<String> recipients;
    private final JavaMailSender emailSender;

    public EmailServiceImpl(@Value("${notifications.emails}") List<String> recipients, JavaMailSender emailSender) {
        this.recipients = recipients;
        this.emailSender = emailSender;
    }

    @Override
    public void send(EmailType type, Object... params) {
        switch (type) {
            case NORMAL ->
                    /*
                     * params structure must be like ["to", "subject","text"]
                     */
                    sendToRecipients(params[0].toString(), params[1].toString(), params[2].toString());
            case ALERT ->
                    /*
                     * params must be like ["subject","text"]
                     * Don't need "to" because the mail will be sent directly to private recipients
                     */
                    sendToRecipients(params[0].toString(), params[1].toString());
            case RATE -> sendForRate(params[0].toString(), params[1].toString(), (int) params[2]);
            default -> sendForProposal(params[0].toString(), params[1] != null
                            ? params[1].toString()
                            : msg("notification.it.does.not.have"),
                    params[2].toString(), params[3].toString(), params[4] != null
                            ? params[4].toString()
                            : msg("notification.it.does.not.have"),
                    (SelectIndicativeOfferTO) params[5]);
        }
    }

    //region Sends a simple mail message, formed from given params.
    private void sendToRecipients(String to, String subject, String text) {
        try {
            emailSender.send(EMAIL_BINDER.build(to, subject, text));
        } catch (MailException e) {
            log.error(e.getLocalizedMessage());
        }
    }
    //endregion

    //region Sends a simple mail message, formed from given params, when a client rates the system.
    private void sendForRate(String email, String comment, int rating) {
        try {
            var subject = msg("notification.email.evaluation.for.linkapital");
            var text = msg("notification.email.rating.points.comments", email, rating, comment);
            sendToRecipients(subject, text);
        } catch (MailException e) {
            log.error(e.getMessage());
        }
    }
    //endregion

    //region Sends a simple mail message, formed from given params, when a client accepts a proposal.
    private void sendForProposal(String email, String phone, String cnpj, String socialReason,
                                 String fantasyName, SelectIndicativeOfferTO to) {
        try {
            var subject = msg("notification.email.user.requested.indicative.proposal");
            var text = msg("notification.email.proposal.content", email, phone, cnpj, socialReason,
                    fantasyName, messageOfferConfirmToEmail(to));
            sendToRecipients(subject, text);
        } catch (MailException e) {
            log.error(e.getMessage());
        }
    }
    //endregion

    //region Concatenate and format a string with the confirmed offers that will be sent by mail
    private String messageOfferConfirmToEmail(SelectIndicativeOfferTO to) {
        var messageOfferConfirm = msg("notification.email.message.offers.confirmed");

        if (to.isOfferOne())
            messageOfferConfirm += messageOfferConfirm(1);

        if (to.isOfferTwo())
            messageOfferConfirm += messageOfferConfirm(2);

        if (to.isOfferThree())
            messageOfferConfirm += messageOfferConfirm(3);

        if (to.isOfferFour())
            messageOfferConfirm += messageOfferConfirm(4);

        return messageOfferConfirm;
    }
    //endregion

    //region Confirmed offer number message that will be concatenated in the email that is send
    private String messageOfferConfirm(int learningNumber) {
        return msg("notification.email.offer.attribute", learningNumber);
    }
    //endregion

    //region Sends a simple mail message, formed from given params.
    private void sendToRecipients(String subject, String text) {
        recipients.forEach(recipient -> emailSender.send(EMAIL_BINDER.build(recipient, subject, text)));
    }
    //endregion

}
