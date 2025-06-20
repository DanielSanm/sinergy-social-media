package com.sinergy.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflicException extends RuntimeException {

	public ResourceConflicException(String message) {
		super(message);
	}
}
