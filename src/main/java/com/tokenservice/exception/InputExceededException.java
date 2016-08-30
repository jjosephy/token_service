package com.tokenservice.exception;

import com.tokenservice.errorcode.ErrorCodes;

/**
 * Thrown if an Request Body is too large, preventing any issues from with too large of 
 * input strings being passed. We only type this to manage internally, return nothing useful
 * to clients since in this case this is something malicious detected.
 *
 * @author Jason Josephy
 */
public class InputExceededException extends GeneralAuthException{

	/**
	 * Serial Version Id
	 */
	private static final long serialVersionUID = 2709466156004990170L;

	// ~ Constructors
	// ===================================================================================================
	
	/**
	 * Constructs an <code>InputExceededException</code> with a blank message.
	 *
	 */
	public InputExceededException() {
		super("");
	}
	
	/**
	 * Constructs an <code>InputExceededException</code> with the specified message.
	 *
	 * @param msg the detail message
	 */
	public InputExceededException(String msg) {
		super(msg);
	}
	
	/**
	 * Error code <code>ErrorCodes</code> INPUT_EXCEEDED
	 */
	@Override
	public int getErrorCode() {
		return ErrorCodes.INPUT_EXCEEDED;
		
	}
}