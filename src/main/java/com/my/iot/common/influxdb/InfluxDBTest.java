package com.my.iot.common.influxdb;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import com.my.iot.common.exception.InfluxDBConnectionFullException;
import com.my.iot.common.exception.MyRuntimeException;
import com.my.iot.common.influxdb.connection.InfluxDBConnection;
import com.my.iot.common.influxdb.connection.InfluxDBConnectionFactory;

/**
 * InfluxDBTest.java
 * InfluxDB의 DB Connection을 관리해주는 클래스.
 * 여유가 되면, 캐시 적용과 Connection Pool 적용 등의 추가가 필요하다.
 * 현재 인기있는 ORM Framework에서는 InfluxDB를 지원하지 않는 듯 하여, 직접 커넥션을 맺고 관리해야 함.
 * @author 효민영♥
 *
 */
public class InfluxDBTest {
	
	// Test용 쿼리.
	public static List<Float> myTest() throws MyRuntimeException {
		InfluxDBConnection con;
		
		try {
			con = InfluxDBConnectionFactory.getConnection();
		} catch (InfluxDBConnectionFullException e) {
			throw new MyRuntimeException();
		}
		
		InfluxDB db = con.getDb();
		
		db.setDatabase("telegraf");
		Query query = new Query("SELECT Percent_Processor_Time FROM win_cpu ORDER BY time DESC LIMIT 20", "telegraf");
		if(!db.isBatchEnabled()) db.enableBatch(BatchOptions.DEFAULTS);
		QueryResult ret = db.query(query);
		
		List<Float> data = new LinkedList<Float>();
		
		ret.getResults()
			.get(0)
			.getSeries()
			.get(0)
			.getValues()
			.stream()
			.forEach((value)->{data.add(Float.parseFloat(String.format("%.2f",value.get(1))));});
		
		
		//사용을 다 한 뒤에, 반환한다.
		InfluxDBConnectionFactory.endConnection(con);
		return data;
	}
	
	// DashboardService쪽과 로직이 같음. (초기 테스트용으로 사용한 버전.)
	public static QueryResult myTest2() throws MyRuntimeException {
		InfluxDBConnection con = null;
		try {
			con = InfluxDBConnectionFactory.getConnection();
			InfluxDB db = con.getDb();
			db.setDatabase("telegraf");
			Query query = new Query("SELECT Available_Bytes FROM win_mem ORDER BY time desc limit 50", "telegraf");
			if(!db.isBatchEnabled()) db.enableBatch(BatchOptions.DEFAULTS);
			QueryResult ret = db.query(query);
			// 역순으로 최신순으로 뽑았기 때문에, 배열을 한번 뒤집어 준다.
			Collections.reverse(ret.getResults().get(0).getSeries().get(0).getValues());
			return ret;
		} catch (InfluxDBConnectionFullException e) {
			throw new MyRuntimeException();
		} finally {
			InfluxDBConnectionFactory.endConnection(con);
		}
	}
	
}
