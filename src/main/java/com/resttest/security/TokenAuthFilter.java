package com.resttest.security;

import com.google.common.base.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class TokenAuthFilter extends GenericFilterBean {

    private static final String HEADER_NAME = "x-auth-token";

    private final AuthenticationManager authenticationManager;

    private AuthenticationEntryPoint authenticationEntryPoint;

    private boolean ignoreFault = false;

    public TokenAuthFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        try {
            String token = getTokenFromCookies(httpServletRequest);
            if (Strings.isNullOrEmpty(token)) {
                throw new TokenAuthenticationHeaderNotFound("Header " + HEADER_NAME + " is not found.", null);
            }

            Authentication authentication = authenticationManager.authenticate(new TokenAuthentication(token));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (AuthenticationException authenticationException) {
            if (!ignoreFault) {
                authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, authenticationException);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    public String getTokenFromCookies(HttpServletRequest request) {
        return Arrays.stream(request.getCookies()).filter(p -> p.getName().equals(HEADER_NAME))
                .findFirst().get().getValue();
    }
}
