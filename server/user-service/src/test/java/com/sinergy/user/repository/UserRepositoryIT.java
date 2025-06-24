package com.sinergy.user.repository;

import static com.sinergy.user.controller.UserProfileControllerTest.createTestUser;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.sinergy.user.config.JpaConfig;
import com.sinergy.user.entity.User;
import com.sinergy.user.test.IntegrationTestBase;

@Import(JpaConfig.class)
public class UserRepositoryIT extends IntegrationTestBase {

	private User testUser;
	
	@BeforeEach
	void setUp() {
		testUser = userRepository.save(createTestUser());
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	void shouldFindUserByUsername() { 
		final var result = userRepository.findByUsername(testUser.getUsername());
		
		assertThat(result).isPresent();
		assertThat(result.get().getUsername()).isEqualTo(testUser.getUsername());
	}
	
    @Test
    void shouldCheckIfUserExistsByUsername() {
        final var exists = userRepository.existsByUsername(testUser.getUsername());
        assertThat(exists).isTrue();
    }
    
    @Test
    void shouldCheckIfUserExistsByEmail() {
        final var exists = userRepository.existsByEmail(testUser.getEmail());
        assertThat(exists).isTrue();
    }
	
	@Test
	void shouldFindUserById() {
		final var result = userRepository.findById(testUser.getId());
		
		assertThat(result).isPresent();
		assertThat(result.get().getUsername()).isEqualTo(testUser.getUsername());
		assertThat(result.get().getEmail()).isEqualTo(testUser.getEmail());
	}
}
