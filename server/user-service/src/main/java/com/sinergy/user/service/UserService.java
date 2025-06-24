package com.sinergy.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sinergy.user.entity.User;
import com.sinergy.user.exception.ResourceConflicException;
import com.sinergy.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"The user account has been deleted or inactivated"));
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"The user account has been deleted or inactivated"));
	}
	
	@Transactional
	public User register(User user) {
		
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new ResourceConflicException("Username [%s] is already taken"
					.formatted(user.getUsername()));
		}
		
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new ResourceConflicException("Email [%s] is already taken"
					.formatted(user.getEmail()));
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
}
