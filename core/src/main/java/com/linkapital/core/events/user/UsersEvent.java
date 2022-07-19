package com.linkapital.core.events.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Class used to send a Spring event, the Envelop class is used to transport the objects that the event contains
 * {@see Envelop}
 */
@Getter
public class UsersEvent extends ApplicationEvent {

    private final Object[] objects;

    @Builder
    public UsersEvent(Object source, Object[] objects) {
        super(source);
        this.objects = objects;
    }

}
