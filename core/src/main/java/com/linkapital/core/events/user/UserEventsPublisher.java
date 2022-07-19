package com.linkapital.core.events.user;

import com.linkapital.core.services.security.datasource.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public class UserEventsPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public UserEventsPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Send an event to store multiple users
     *
     * @param users {@link List<User>} a list of users
     */
    public void saveAll(List<User> users) {
        applicationEventPublisher.publishEvent(buildUsersEvent(1, users));
    }

    /**
     * Send an event to store a user
     *
     * @param user {@link User}
     */
    public void save(User user) {
        applicationEventPublisher.publishEvent(buildUsersEvent(2, user));
    }

    /**
     * Send an event to delete user and company relationships
     *
     * @param email {@link String} the user email
     */
    public void deleteCnpjFromList(String email, String cnpj) {
        applicationEventPublisher.publishEvent(buildUsersEvent(3, email, cnpj));
    }

    private UsersEvent buildUsersEvent(Object... objects) {
        return UsersEvent
                .builder()
                .source(this)
                .objects(objects)
                .build();
    }

}
