package com.tokenservice.contract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to support a generic error type that can be used to communicate service
 * errors to clients.
 * 
 * @author q4vy
 *
 */
public class ErrorContract extends ContractBase {

	/**
	 * The error code associated with the error type.
	 */
	private final int code;

	/**
	 * The error message associated with the error type.
	 */
	private final String message;

	/**
	 * The Date Time stamp when the error occurred.
	 */
	private final Date timeStamp;

	/**
	 * Default Constructor
	 */
	public ErrorContract() {
		this.code = 0;
		this.message = "";
		this.timeStamp = new Date();
	}

	/**
	 * Overloaded Constructor
	 * 
	 * @param errorCode
	 * @param errorMessage
	 */
	public ErrorContract(int errorCode, String errorMessage) {
		this.code = errorCode;
		this.message = errorMessage;
		this.timeStamp = new Date();
	}

	/**
	 * Gets the error code of the error.
	 * 
	 * @return
	 */
	public int getCode() {
		return this.code;
	}

	/**
	 * Gets the message of the error.
	 * 
	 * @return
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Gets the DateTime stamp of the error.
	 * 
	 * @return
	 */
	public String getTimeStamp() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(this.timeStamp);
		} catch (Exception ex) {
			return ex.getMessage();
		}
		
	}
}