package com.my.iot.dashboard.test;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

public class InfluxDBTest {
	public static QueryResult myTest() {
		
		InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:3000", "telegraf", "telegraf");
//		String dbName = "testdbbbbnb";
//		
//		influxDB.query(new Query("CREATE DATABASE " + dbName , dbName));
//		influxDB.setDatabase(dbName);

//		influxDB.write(Point.measurement("cpu")
//		    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
//		    .addField("idle", 90L)
//		    .addField("user", 9L)
//		    .addField("system", 1L)
//		    .build());
//
//		influxDB.write(Point.measurement("disk")
//		    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
//		    .addField("used", 80L)
//		    .addField("free", 1L)
//		    .build());
		influxDB.setDatabase("telegraf");
		Query query = new Query("SELECT Percent_Processor_Time FROM win_cpu", "telegraf");
		influxDB.enableBatch(BatchOptions.DEFAULTS);
		QueryResult ret = influxDB.query(query);
		
		influxDB.close();
		return ret;
	}
	
}
