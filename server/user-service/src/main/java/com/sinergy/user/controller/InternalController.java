package com.sinergy.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinergy.user.dto.UserCredentialsDto;
import com.sinergy.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
@Slf4j
public class InternalController {
	
	private final UserService userService;
	
	@GetMapping("/user/credentials/{username}")
	public ResponseEntity<UserCredentialsDto> getUserCredentials(@PathVariable String username) {
		log.info("Starting Get User Credentials");
		final var user = userService.getUserByUsername(username);
		return ResponseEntity.ok(new UserCredentialsDto(user.getUsername(), user.getPassword()));
	}
}
