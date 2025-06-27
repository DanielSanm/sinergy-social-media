package com.sinergy.user.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

	private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

    	String path = request.getRequestURI();
    	if (path.startsWith("/internal/")) {
    		String apiKey = request.getHeader("X-INTERNAL-API-KEY");

            if (apiKey != null) {
                try {
                    Authentication auth = authenticationManager.authenticate(new ApiKeyAuthenticationToken(apiKey));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } catch (AuthenticationException ex) {
                	log.error("Invalid API Key - " + ex.getMessage());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing API Key");
                return;
            }
    	}
        
        filterChain.doFilter(request, response);
    }
}