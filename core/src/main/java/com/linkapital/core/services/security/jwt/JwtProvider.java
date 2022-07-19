package com.linkapital.core.services.security.jwt;

import com.linkapital.core.services.security.InvalidJwtAuthenticationException;
import io.jsonwebtoken.JwtException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static io.jsonwebtoken.Jwts.parser;
import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtProvider {

    private final String secretKey;
    private final UserDetailsService customUserDetailsService;

    public JwtProvider(UserDetailsService customUserDetailsService,
                       @Value("${security.jwt.token.secret-key}") @NonNull String secretKey) {
        this.customUserDetailsService = customUserDetailsService;
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Obtain Authentication
     *
     * @param username {@link String} the username
     * @return {@link Authentication}
     */
    public Authentication getAuthentication(String username) {
        var userDetails = this.customUserDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Resolve Authentication Token from request
     *
     * @param req {@link HttpServletRequest} the original request
     * @return {@link String}
     */
    public String resolveToken(@NonNull HttpServletRequest req) {
        return parseBearerToken(req.getHeader("Authorization"));
    }

    /**
     * Parse token
     *
     * @param bearerToken {@link String} the token
     * @return {@link String}
     */
    public String parseBearerToken(String bearerToken) {
        return hasText(bearerToken) && bearerToken.startsWith("Bearer ")
                ? bearerToken.substring(7)
                : null;
    }

    /**
     * Validate token
     *
     * @param token {@link String} the token
     */
    public void validateTokenAndSetAuthentication(String token) {
        try {
            var claims = parser().setSigningKey(secretKey).parseClaimsJws(token);
            var username = claims.getBody().getSubject();
            var expired = claims.getBody().getExpiration().before(new Date());

            if (username == null || expired)
                throw new InvalidJwtAuthenticationException(msg("jwt.expired.or.invalid"));

            var userDetails = this.customUserDetailsService.loadUserByUsername(username);
            if (!userDetails.isEnabled())
                throw new InvalidJwtAuthenticationException(msg("security.user.disabled"));

            SecurityContextHolder.getContext().setAuthentication(this.getAuthentication(username));
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException(msg("jwt.expired.or.invalid"));
        }

    }

}
