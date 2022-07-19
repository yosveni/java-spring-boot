package com.linkapital.core.configuration;

import com.linkapital.core.services.security.jwt.JwtProvider;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static com.linkapital.core.services.security.contract.enums.Authority.ANONYMOUS;
import static org.springframework.messaging.simp.stomp.StompCommand.CONNECT;
import static org.springframework.messaging.support.MessageHeaderAccessor.getAccessor;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static final String IP_ADDRESS = "IP_ADDRESS";
    private final JwtProvider jwtProvider;

    public WebSocketConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/websocket")
                //.setAllowedOrigins("http://localhost:4200")//poner ip del front
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(defaultHandshakeHandler())
                .withSockJS()
                .setInterceptors(httpSessionHandshakeInterceptor());
    }

    @Bean
    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(
                    @NonNull ServerHttpRequest request,
                    @NonNull ServerHttpResponse response,
                    @NonNull WebSocketHandler wsHandler,
                    @NonNull Map<String, Object> attributes) {
                if (request instanceof ServletServerHttpRequest servletServerHttpRequest)
                    attributes.put(IP_ADDRESS, servletServerHttpRequest.getRemoteAddress());

                return true;
            }

            @Override
            public void afterHandshake(
                    @NonNull ServerHttpRequest request,
                    @NonNull ServerHttpResponse response,
                    @NonNull WebSocketHandler wsHandler,
                    Exception exception
            ) {
                // empty just to override
            }
        };
    }

    private DefaultHandshakeHandler defaultHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(@NonNull ServerHttpRequest request,
                                              @NonNull WebSocketHandler wsHandler,
                                              @NonNull Map<String, Object> attributes) {
                var authorities = new ArrayList<SimpleGrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(ANONYMOUS.toString()));
                return Optional
                        .ofNullable(request.getPrincipal())
                        .orElse(new AnonymousAuthenticationToken("WebsocketConfiguration", "anonymous",
                                authorities));
            }
        };
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
                var accessor = getAccessor(message, StompHeaderAccessor.class);
                if (accessor != null && CONNECT.equals(accessor.getCommand())) {
                    var bearerToken = jwtProvider.parseBearerToken(
                            Optional
                                    .ofNullable(accessor.getNativeHeader("Authorization"))
                                    .map(g -> g.get(0))
                                    .orElse(null)
                    );
                    jwtProvider.validateTokenAndSetAuthentication(bearerToken);
                    accessor.setUser(getContext().getAuthentication());
                }

                return message;
            }
        });
    }

}
