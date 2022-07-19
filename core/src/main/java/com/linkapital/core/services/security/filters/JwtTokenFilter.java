package com.linkapital.core.services.security.filters;

import com.linkapital.core.services.security.InvalidJwtAuthenticationException;
import com.linkapital.core.services.security.jwt.JwtProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    public JwtTokenFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        var token = jwtProvider.resolveToken((HttpServletRequest) req);

        try {
            if (token != null)
                jwtProvider.validateTokenAndSetAuthentication(token);
            filterChain.doFilter(req, res);
        } catch (InvalidJwtAuthenticationException | UsernameNotFoundException e) {
            ((HttpServletResponse) res).sendError(401, e.getMessage());
        }
    }

}