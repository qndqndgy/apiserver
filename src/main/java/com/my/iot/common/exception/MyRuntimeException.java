package com.my.iot.common.exception;

import org.json.simple.JSONObject;

public class MyRuntimeException extends Exception {

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
