package com.sinergy.user.service;

import static com.sinergy.user.controller.UserProfileControllerTest.createTestUser;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sinergy.user.entity.User;
import com.sinergy.user.exception.ResourceConflicException;
import com.sinergy.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserService userService;
	
	private User user;
	
	@BeforeEach
	void setUp() {
		user = createTestUser();
	}
	
	@Test
	void shouldRegisterUserIfUserNoExists() {
		final var user = createTestUser();
		
		when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
		when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
		when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		userService.register(user);
		
		verify(userRepository).save(any(User.class));
	}
	
    @Test
    void shouldThrowExceptionIfEmailExists() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.register(user))
                .isInstanceOf(ResourceConflicException.class);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionIfUsernameExists() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> userService.register(user))
                .isInstanceOf(ResourceConflicException.class);

        verify(userRepository, never()).save(any(User.class));
    }
	
}
