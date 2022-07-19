package com.linkapital.core.services.security.jwt;

import com.linkapital.core.exceptions.InvalidRefreshTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.jsonwebtoken.Jwts.claims;
import static io.jsonwebtoken.Jwts.parser;

@Component
public class TokenProvider {

    private static final String KEY_ID = "id";
    private static final String KEY_ROLES = "roles";
    private static final String KEY_USERNAME = "username";
    private final long validityInMilliseconds;
    private final long validityRefreshTokenInMilliseconds;
    private final String secretKey;

    public TokenProvider(@Value("${security.jwt.token.expire-length}") long validityInMilliseconds,
                         @Value("${security.jwt.token.refresh-token}") long validityRefreshTokenInMilliseconds,
                         @Value("${security.jwt.token.secret-key}") String secretKey) {
        this.validityInMilliseconds = validityInMilliseconds;
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityRefreshTokenInMilliseconds = validityRefreshTokenInMilliseconds;
    }

    public String generateToken(String id, String username, List<String> roles, boolean refreshToken) {
        var claims = claims().setSubject(username);
        claims.put(KEY_ID, id);
        claims.put(KEY_ROLES, roles);
        claims.put(KEY_USERNAME, username);

        var now = new Date();
        var validity = new Date(now.getTime() + (refreshToken
                ? validityRefreshTokenInMilliseconds
                : validityInMilliseconds));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Map<String, String> refreshToken(String refreshToken) throws InvalidRefreshTokenException {
        Jws<Claims> claims;
        try {
            claims = parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
        } catch (SignatureException | MalformedJwtException e) {
            throw new InvalidRefreshTokenException(e.getMessage());
        }

        var result = new HashMap<String, String>();
        result.put("token", generateToken(claims.getBody().get(KEY_ID).toString(), claims.getBody().get(KEY_USERNAME)
                .toString(), (List<String>) claims.getBody().get(KEY_ROLES), false));
        result.put("refreshToken", generateToken(UUID.randomUUID().toString(), claims.getBody().get(KEY_USERNAME)
                .toString(), (List<String>) claims.getBody().get(KEY_ROLES), true));

        return result;
    }

}
