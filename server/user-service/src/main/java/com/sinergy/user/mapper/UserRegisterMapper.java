package com.sinergy.user.mapper;

import org.springframework.stereotype.Component;

import com.sinergy.user.dto.UserRegisterRequestDto;
import com.sinergy.user.dto.UserRegisterResponseDto;
import com.sinergy.user.entity.User;

@Component
public class UserRegisterMapper {
	
	public UserRegisterResponseDto toRegisterResponseDto(User user) {
		return new UserRegisterResponseDto(user.getUsername(), user.getEmail());
	}
	
	public User toEntity(UserRegisterRequestDto userDto) {
		final User user = new User();
		user.setUsername(userDto.username());
		user.setEmail(userDto.email());
		user.setPassword(userDto.password());
		
		return user;
	}
}
