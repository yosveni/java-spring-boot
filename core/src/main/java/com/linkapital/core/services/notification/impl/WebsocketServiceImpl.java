package com.linkapital.core.services.notification.impl;

import com.linkapital.core.services.notification.WebsocketService;
import com.linkapital.core.services.notification.contract.enums.WebsocketBroker;
import com.linkapital.core.services.notification.contract.to.WebsocketPayloadTO;
import com.linkapital.core.services.security.contract.enums.Code;
import com.linkapital.core.services.security.datasource.domain.User;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;

@Service
public class WebsocketServiceImpl implements WebsocketService {

    private final SimpMessagingTemplate template;
    private final SimpUserRegistry simpUserRegistry;

    public WebsocketServiceImpl(SimpMessagingTemplate template,
                                SimpUserRegistry simpUserRegistry) {
        this.template = template;
        this.simpUserRegistry = simpUserRegistry;
    }

    @Override
    public void dispatch(Collection<String> recipientsEmails,
                         WebsocketBroker prefix,
                         String destination,
                         String message,
                         Object payload) {
        var recipients = simpUserRegistry.getUsers()
                .stream()
                .filter(simpUser -> recipientsEmails.contains(simpUser.getName()))
                .collect(Collectors.toSet());
        dispatchToUsers(recipients, prefix, destination, message, payload);
    }

    @Override
    public void dispatch(List<Code> codes, WebsocketBroker prefix, String destination, String message, Object payload) {
        var recipients = simpUserRegistry.getUsers()
                .stream()
                .filter(websocketUser -> websocketUser.getPrincipal() != null &&
                        existInList(websocketUser, codes))
                .collect(Collectors.toSet());
        dispatchToUsers(recipients, prefix, destination, message, payload);
    }

    private void dispatchToUsers(Set<SimpUser> users,
                                 WebsocketBroker prefix,
                                 String destination,
                                 String message,
                                 Object payload) {
        users.stream()
                .flatMap(simpUser -> simpUser.getSessions().stream())
                .forEach(session -> {
                    var headerAccessor = SimpMessageHeaderAccessor.create(MESSAGE);
                    if (!headerAccessor.isMutable())
                        headerAccessor.setLeaveMutable(true);

                    headerAccessor.setSessionId(session.getId());
                    template.convertAndSendToUser(
                            session.getId(),
                            prefix.toString().toLowerCase(Locale.ROOT) + "/" + destination,
                            new WebsocketPayloadTO()
                                    .withMessage(message)
                                    .withData(payload),
                            headerAccessor.getMessageHeaders());
                });
    }

    private boolean existInList(SimpUser simpUser, List<Code> codes) {
        var principal = (UsernamePasswordAuthenticationToken) simpUser.getPrincipal();
        if (principal == null)
            return false;

        var code = ((User) principal.getPrincipal()).getRole().getCode();
        return codes
                .stream()
                .anyMatch(c -> c.name().equals(code));
    }

}
