package com.my.iot.common.exception;

import org.json.simple.JSONObject;

public class MyDeniedException extends Exception {

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
