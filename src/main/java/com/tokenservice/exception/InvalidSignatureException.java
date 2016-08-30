package com.tokenservice.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidSignatureException extends AuthenticationException{
	private static final long serialVersionUID = 1L;

	public InvalidSignatureException(String msg) {
		super(msg);
	}

}
