package com.sinergy.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sinergy.auth.client.UserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemoteUserDetailsService implements UserDetailsService {

	@Value("${user-service.api-key}")
	private String apiKey;
	
	private final UserClient userClient;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Get User by user-service");
        return userClient.findByUsername(username, apiKey).map(user -> User.builder()
                .username(username)
                .password(user.password())
                .build())
                .orElseThrow(() -> new UsernameNotFoundException("User with username [%s] not found".formatted(username)));
	}

}
