package com.sinergy.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.sinergy.user.test.IntegrationTestBase;

@AutoConfigureMockMvc
public class UserProfileControllerIT extends IntegrationTestBase {

	private final String USER_JSON = """
            {
            "username": "john_doe",
            "email": "john@example.com",
            "password": "123456789"
          }
          """;
	private final String INVALID_USER_JSON = """
            {
            "username": "do",
            "email": "email",
            "password": "123"
          }
          """;
	
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRegisterUser() throws Exception {
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USER_JSON))
                .andExpect(status().isCreated());
    }
    
    @Test
    void shouldFailToRegisterUser() throws Exception {        
        mockMvc.perform(post("/api/user/register")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(INVALID_USER_JSON))
                .andExpect(status().isBadRequest());
    }
    
}
