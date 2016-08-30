package com.tokenservice.exception;

import org.springframework.security.core.AuthenticationException;

public class UnauthenticatedException extends AuthenticationException{

	private static final long serialVersionUID = 1L;

	public UnauthenticatedException() {
		super("Unauthenticated");
	}

}
