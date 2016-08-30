package com.tokenservice.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException{

	private static final long serialVersionUID = 1L;

	public InvalidTokenException() {
		super("Invalid Token");
	}
	
	public InvalidTokenException(String msg) {
		super(msg);
	}

}
