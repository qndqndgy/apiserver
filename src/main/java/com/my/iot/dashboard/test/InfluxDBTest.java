package com.my.iot.dashboard.test;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

public class InfluxDBTest {
	public static List<Float> myTest() {
		
		InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:3000", "telegraf", "telegraf");
		
		influxDB.setDatabase("telegraf");
		Query query = new Query("SELECT Percent_Processor_Time FROM win_cpu ORDER BY time DESC LIMIT 20", "telegraf");
		influxDB.enableBatch(BatchOptions.DEFAULTS);
		QueryResult ret = influxDB.query(query);
		
		List<Float> data = new LinkedList<Float>();
		
		ret.getResults()
			.get(0)
			.getSeries()
			.get(0)
			.getValues()
			.stream()
			.forEach((value)->{data.add(Float.parseFloat(String.format("%.2f",value.get(1))));});
		
		influxDB.close();
		return data;
	}
	
	public static QueryResult myTest2() {
		
		InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:3000", "telegraf", "telegraf");
		
		influxDB.setDatabase("telegraf");
		Query query = new Query("SELECT Available_Bytes FROM win_mem ORDER BY time desc limit 50", "telegraf");
		influxDB.enableBatch(BatchOptions.DEFAULTS);
		QueryResult ret = influxDB.query(query);
		// 역순으로 최신순으로 뽑았기 때문에, 배열을 한번 뒤집어 준다.
		Collections.reverse(ret.getResults().get(0).getSeries().get(0).getValues());
		return ret;
	}
	
}
