package com.tokenservice.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidRequestBodyException extends AuthenticationException{

	private static final long serialVersionUID = 1L;

	public InvalidRequestBodyException() {
		super("Cant parse request body");
	}
}
