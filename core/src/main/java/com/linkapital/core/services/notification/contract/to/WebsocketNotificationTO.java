package com.linkapital.core.services.notification.contract.to;

import com.linkapital.core.services.notification.contract.enums.WebsocketBroker;
import com.linkapital.core.services.security.datasource.domain.User;
import lombok.Getter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static java.util.Collections.singleton;

@Getter
public class WebsocketNotificationTO {

    private Collection<User> recipients;

    private WebsocketBroker prefix;

    private String destination;

    private String message;

    private Object payload;

    public WebsocketNotificationTO(Collection<User> recipients,
                                   WebsocketBroker prefix,
                                   String destination,
                                   String message,
                                   Object... payload) {
        this.recipients = recipients;
        this.prefix = prefix;
        this.destination = destination;
        this.message = message;
        this.payload = payload;
    }

    public WebsocketNotificationTO(WebsocketBroker prefix,
                                   String destination,
                                   String message,
                                   Object payload) {
        this.recipients = singleton((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        this.prefix = prefix;
        this.destination = destination;
        this.message = message;
        this.payload = payload;
    }


    public WebsocketNotificationTO withMessage(String message) {
        this.message = message;
        return this;
    }

    public WebsocketNotificationTO withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public WebsocketNotificationTO withPrefix(WebsocketBroker prefix) {
        this.prefix = prefix;
        return this;
    }

    public WebsocketNotificationTO withRecipients(Collection<User> receivers) {
        this.recipients = receivers;
        return this;
    }

}
