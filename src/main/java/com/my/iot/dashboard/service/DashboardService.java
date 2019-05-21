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
	
	// TC작성 필요
	public QueryResult getMemoryData() throws MyRuntimeException {
		
		long currTimeHash = LocalTime.now().getHour()*10000 + LocalTime.now().getMinute()*100 + LocalTime.now().getSecond();
		// Ex)
		// 21시 47분 5초인 경우 아래와 같은 규칙으로, 각 시분초마다 유일한 해시값을 만들 수 있다.
		// 210000 + 4700 + 5 = 214705 
		
		if(cache.get(currTimeHash)!=null) {
			cacheHitCount.incrementAndGet();
			return cache.get(currTimeHash);
		}
		
		InfluxDBConnection con = null;
		QueryResult ret = null;
		
		// InfluxDB Connection을 따로 관리해주는 ORM 프레임워크가 없어서, 직접 만들었다.
		try {
			con = InfluxDBConnectionFactory.getConnection();
			// 수집 Data는 telegraf 에이전트가 알아서 넣어줌.
			InfluxDB db = con.getDb();
			db.setDatabase("telegraf");
			
			StringBuilder queryBuild = new StringBuilder().append("SELECT Available_Bytes ")
															.append("FROM win_mem ")
															.append("ORDER BY time DESC ")
															.append("LIMIT 100");
			
			Query query = new Query(queryBuild.toString(), "telegraf");
			if(!db.isBatchEnabled()) db.enableBatch(BatchOptions.DEFAULTS);
			ret = db.query(query);
			// 역순으로 최신순으로 뽑았기 때문에, 배열을 한번 뒤집어 준다.
			Collections.reverse(ret.getResults().get(0).getSeries().get(0).getValues());
			//cache에 저장
			synchronized(cache) {
				// 캐시 저장 시, Thread-Safe 문제 발생할 수 있으므로 안전하게 lock.
				cache.put(currTimeHash, ret);
			}
		} catch (InfluxDBConnectionFullException e) {
			throw new MyRuntimeException();
		} finally {
			// InfluxDB Connection을 다 썼으면, 아래와 같이 반납해주어야 한다.
			InfluxDBConnectionFactory.endConnection(con);
		}
		
		return ret;
	}
	
}
