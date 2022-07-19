package com.linkapital.core.events.user;

import com.linkapital.core.services.security.UserService;
import com.linkapital.core.services.security.datasource.domain.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Component that listens for events contained in the UsersEvent class,
 * if the object of the Envelop class is a list it creates several users, otherwise it creates a single user
 * <p>
 * {@see UsersEvent}
 * {@see Envelop}
 */
@Component
public class UsersEventsListener implements ApplicationListener<UsersEvent> {

    private final UserService userService;

    public UsersEventsListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(UsersEvent event) {
        switch ((int) event.getObjects()[0]) {
            case 1 -> userService.saveAll((List<User>) event.getObjects()[1]);
            case 2 -> userService.save((User) event.getObjects()[1]);
            default -> {
            }
        }
    }

}
