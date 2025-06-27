package com.sinergy.user.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {
	
	@Value("${internal.api-key}")
    private String expectedApiKey;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String apiKey = (String) authentication.getCredentials();

        if (expectedApiKey.equals(apiKey)) {
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_INTERNAL"));
            log.info("API Key authentication successfully");
            return new ApiKeyAuthenticationToken("internal-service", authorities);
        }
        log.error("Invalid API Key");
        throw new BadCredentialsException("Invalid API Key");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
