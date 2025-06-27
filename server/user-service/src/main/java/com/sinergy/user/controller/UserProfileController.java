package com.sinergy.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinergy.user.dto.UserRegisterRequestDto;
import com.sinergy.user.dto.UserRegisterResponseDto;
import com.sinergy.user.entity.User;
import com.sinergy.user.mapper.UserRegisterMapper;
import com.sinergy.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {
	
	private final UserService userService;
	private final UserRegisterMapper userMapper;
	
	@GetMapping("/id/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@GetMapping("/name/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserByUsername(username));
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserRegisterResponseDto> registerUser(@Valid @RequestBody UserRegisterRequestDto userRequest) {
		final var resp = userService.register(userMapper.toEntity(userRequest));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(userMapper.toRegisterResponseDto(resp));
	}
	
}
