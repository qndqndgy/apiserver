package com.my.iot.common.exception;

import org.json.simple.JSONObject;

public class InfluxDBConnectionFullException extends Exception {

	public InfluxDBConnectionFullException() {
		super();
	}

	public InfluxDBConnectionFullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace , JSONObject jsonObject) {
		super(message + " Connection Pool is Full.. Need to take a time...", cause, enableSuppression, writableStackTrace);
	}

	public InfluxDBConnectionFullException(String message, Throwable cause , JSONObject jsonObject) {
		super(message + " Connection Pool is Full.. Need to take a time...", cause);
	}

	public InfluxDBConnectionFullException(String message , JSONObject jsonObject) {
		super(message + " Connection Pool is Full.. Need to take a time...");
	}

	public InfluxDBConnectionFullException(Throwable cause , JSONObject jsonObject) {
		super(cause);
	}

}
