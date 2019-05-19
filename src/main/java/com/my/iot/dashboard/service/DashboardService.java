package com.my.iot.dashboard.service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Service;

import com.my.iot.common.exception.InfluxDBConnectionFullException;
import com.my.iot.common.exception.MyRuntimeException;
import com.my.iot.common.influxdb.connection.InfluxDBConnection;
import com.my.iot.common.influxdb.connection.InfluxDBConnectionFactory;

/**
 * DashboardService 클래스
 * Vue 대시보드에서 호출하는 비즈니스 로직이 모여 있음.
 * @author 효민영♥
 *
 */
@Service
public class DashboardService {
	
	// 초 단위로 Data를 메모리에 캐싱한다. 
	// 메모리 관리를 위해, WeakHashMap을 사용하여 다음 Minor GC 시점에 캐시는 메모리에서 제거된다.
	private static final WeakHashMap<Long, QueryResult> cache = new WeakHashMap<Long, QueryResult>(); 
	
	// Cache가 몇 번이나 Hit되는지 확인해보기 위해 추가함.
	public static AtomicLong cacheHitCount = new AtomicLong(0);
	
	public QueryResult getMemoryData() throws MyRuntimeException {
		
		long currTimeHash = LocalTime.now().getHour()*10000 + LocalTime.now().getMinute()*100 + LocalTime.now().getSecond();
		// Ex)
		// 21시 47분 5초인 경우
		// 210000 + 4700 + 5 = 214705 
		
		if(cache.get(currTimeHash)!=null) {
			cacheHitCount.incrementAndGet();
			return cache.get(currTimeHash);
		}
		
		InfluxDBConnection con;
		
		try {
			con = InfluxDBConnectionFactory.getConnection();
		} catch (InfluxDBConnectionFullException e) {
			throw new MyRuntimeException();
		}
		
		InfluxDB db = con.getDb();
		db.setDatabase("telegraf");
		
		StringBuilder queryBuild = new StringBuilder().append("SELECT Available_Bytes ")
														.append("FROM win_mem ")
														.append("ORDER BY time DESC ")
														.append("LIMIT 100");
		
		Query query = new Query(queryBuild.toString(), "telegraf");
		if(!db.isBatchEnabled()) db.enableBatch(BatchOptions.DEFAULTS);
		QueryResult ret = db.query(query);
		// 역순으로 최신순으로 뽑았기 때문에, 배열을 한번 뒤집어 준다.
		Collections.reverse(ret.getResults().get(0).getSeries().get(0).getValues());
		
		InfluxDBConnectionFactory.endConnection(con);
		
		//cache에 저장
		synchronized(cache) {
			cache.put(currTimeHash, ret);
		}
		
		return ret;
	}
	
}
