package com.sinergy.auth.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sinergy.auth.dto.UserCredentialsDto;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserClient {

    @GetMapping("/internal/user/credentials/{username}")
    Optional<UserCredentialsDto> findByUsername(
    		@PathVariable String username,
    		@RequestHeader("X-INTERNAL-API-KEY") String apiKey
    );
}
