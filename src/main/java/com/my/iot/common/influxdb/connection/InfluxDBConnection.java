package com.my.iot.common.influxdb.connection;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

public class InfluxDBConnection {
	
	private boolean idle;
	private InfluxDB db;
	
	InfluxDBConnection(final String connectUri, String username, String password){
		this.db = InfluxDBFactory.connect(connectUri, username, password);
		this.idle = true;
	}
	
	boolean isIdle() {
		return this.idle;
	}
	
	InfluxDBConnection consume() {
		this.idle = false;
		return this;
	}
	
	void returned() {
		this.idle = true;
	}
	
	public InfluxDB getDb() {
		return this.db;
	}
	
	void closeConnection() {
		this.db.close();
	}
}
