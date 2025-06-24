package com.sinergy.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sinergy.user.config.SecurityConfig;
import com.sinergy.user.entity.User;
import com.sinergy.user.exception.ResourceConflicException;
import com.sinergy.user.mapper.UserRegisterMapper;
import com.sinergy.user.repository.UserRepository;
import com.sinergy.user.service.UserService;

@WebMvcTest(UserProfileController.class)
@Import({ UserRegisterMapper.class, SecurityConfig.class})
public class UserProfileControllerTest {
	
	private final String USER_JSON = """
            {
            "username": "john_doe",
            "email": "john@example.com",
            "password": "123456789"
          }
          """;

	@MockitoBean
	private UserService userService;
	
	@MockitoBean
	private UserRepository userRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void shouldRegisterUser() throws Exception {
		when(userService.register(any(User.class))).thenReturn(createTestUser());
		
        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(USER_JSON))
        .andExpect(status().isCreated());
        
        verify(userService).register(any());
	}
	
    @Test
    void shouldFailToRegisterUser() throws Exception {
        doThrow(new ResourceConflicException("Email is already taken")).when(userService).register(any());
        
        mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(USER_JSON))
                .andExpect(status().isConflict());

        verify(userService).register(any());
    }
	
	public static User createTestUser() {
        final var user = new User();
        String unique = UUID.randomUUID().toString();
        user.setUsername(unique + "_user");
        user.setEmail(unique + "@email.com");
        user.setPassword("password");
		return user;
	}
}
