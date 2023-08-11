package com.project.vehiclerental.security.manager;

import com.project.vehiclerental.security.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (jwtAuthenticationProvider.supports(authentication.getClass())) {
            return jwtAuthenticationProvider.authenticate(authentication);
        }

        return authentication;
    }
}