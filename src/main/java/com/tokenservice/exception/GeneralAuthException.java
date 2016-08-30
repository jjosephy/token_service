package com.tokenservice.exception;

import org.springframework.security.core.AuthenticationException;

import com.tokenservice.errorcode.ErrorCodes;

/**
 * Thrown if an General Authentication Exception Occurs, or in the event that details 
 * of the Exception are desired to be masked. 
 *
 * @author Jason Josephy
 */
public class GeneralAuthException extends AuthenticationException{

	/**
	 * Version Id of the Exception
	 */
	private static final long serialVersionUID = 1L;
	private final int errorCode = ErrorCodes.GENERAL_AUTH_EXCEPTION;

	// ~ Constructors
	// ===================================================================================================
	
	/**
	 * Constructs an <code>GeneralAuthException</code> with the specified
	 * message.
	 */
	public GeneralAuthException() {
		super("");
	}
	
	/**
	 * Constructs an <code>GeneralAuthException</code> with the specified
	 * message.
	 *
	 * @param msg the detail message
	 */
	public GeneralAuthException(String msg) {
		super(msg);
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
}

