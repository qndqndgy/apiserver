package com.my.template.common.exception;

import org.json.simple.JSONObject;

/**
 * MyRuntimeException.java
 * @author 효민영♥
 *
 */
public class MyRuntimeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2501438109093917565L;

	public MyRuntimeException() {
		super();
	}

	public MyRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace , JSONObject jsonObject) {
		super(message + " Runtime Exception Occurred. Sorry for inconvinience.", cause, enableSuppression, writableStackTrace);
	}

	public MyRuntimeException(String message, Throwable cause , JSONObject jsonObject) {
		super(message + " Runtime Exception Occurred. Sorry for inconvinience.", cause);
	}

	public MyRuntimeException(String message , JSONObject jsonObject) {
		super(message + " Runtime Exception Occurred. Sorry for inconvinience.");
	}

	public MyRuntimeException(Throwable cause , JSONObject jsonObject) {
		super(cause);
	}

}
