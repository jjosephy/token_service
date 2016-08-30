package com.tokenservice.exception;

import com.tokenservice.errorcode.ErrorCodes;

/**
 * Thrown if an provided version is not supported.
 *
 * @author Jason Josephy
 */
public class UnsupportedAuthVersionException extends GeneralAuthException {

	// ~ Constructors
	// ===================================================================================================
		
	/**
	 * Serial Version Id
	 */
	private static final long serialVersionUID = 2952962143832213163L;

		/**
		 * Constructs an <code>UnsupportedAuthVersionException</code> with a blank message.
		 *
		 */
		public UnsupportedAuthVersionException() {
			super("Unsupported Version");
		}
		
		/**
		 * Constructs an <code>UnsupportedAuthVersionException</code> with the specified message.
		 *
		 * @param msg the detail message
		 */
		public UnsupportedAuthVersionException(String msg) {
			super(msg);
		}
		
		/**
		 * Error code <code>ErrorCodes</code> INPUT_EXCEEDED
		 */
		@Override
		public int getErrorCode() {
			return ErrorCodes.UNSUPPORTED_VERSION;
			
		}
}
