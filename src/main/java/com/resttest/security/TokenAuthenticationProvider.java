package com.resttest.security;

import com.resttest.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;

        String token = (String) tokenAuthentication.getCredentials();
        String user = tokenService.getUser(token);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown token");
        }

        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user,
                token,
                true,
                true,
                true,
                true,
                authList);
        tokenAuthentication.setDetails(userDetails);
        tokenAuthentication.setAuthenticated(true);

        return authentication;
    }

    @Override
    public boolean supports(Class authentication) {
        return authentication == TokenAuthentication.class;
    }
}
