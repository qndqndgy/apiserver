package com.my.template.common.exception;

import org.json.simple.JSONObject;

/**
 * MyDeniedException.java
 * @author 효민영♥
 *
 */
public class MyDeniedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9155774599005504081L;

	public MyDeniedException() {
		super();
	}

	public MyDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace , JSONObject jsonObject) {
		super("You are not authorized. please try to log in using your google account. /login/google ", cause, enableSuppression, writableStackTrace);
	}

	public MyDeniedException(String message, Throwable cause , JSONObject jsonObject) {
		super("You are not authorized. please try to log in using your google account. /login/google ", cause);
	}

	public MyDeniedException(String message , JSONObject jsonObject) {
		super("You are not authorized. please try to log in using your google account. /login/google ");
	}

	public MyDeniedException(Throwable cause , JSONObject jsonObject) {
		super(cause);
	}

}
