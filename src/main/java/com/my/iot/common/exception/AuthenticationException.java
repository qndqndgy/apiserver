package com.my.iot.common.exception;

/**
 * AuthenticationException.java
 * @author 효민영♥
 *
 */
public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6927848656549571461L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message + " Authentication Fail. Plase check your permission.", cause, enableSuppression, writableStackTrace);
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message + " Authentication Fail. Plase check your permission.", cause);
	}

	public AuthenticationException(String message) {
		super(message + " Authentication Fail. Plase check your permission.");
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
	}


}
