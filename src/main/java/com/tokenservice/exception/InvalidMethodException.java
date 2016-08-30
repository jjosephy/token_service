package com.tokenservice.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidMethodException extends AuthenticationException{

	private static final long serialVersionUID = 1L;

	public InvalidMethodException() {
		super("Method Not Supported");
	}

}
