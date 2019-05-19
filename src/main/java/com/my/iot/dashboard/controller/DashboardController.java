package com.my.iot.dashboard.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.iot.common.exception.MyRuntimeException;
import com.my.iot.dashboard.service.DashboardService;

/**
 * Vue대시보드에서 호출하는 Endpoint들을 모아놓은 RestController
 * @author 효민영♥
 *
 */
@RestController
@RequestMapping("/api/dashboard/v1")
public class DashboardController {
	
	@Autowired
	DashboardService service;
	
	private static AtomicLong getMemoryDataApiCallCount = new AtomicLong(0);
	private static AtomicLong getServiceCacheCountApiCallCount = new AtomicLong(0);
	private static AtomicLong getApiCallCountListApiCallCount = new AtomicLong(0);
	
	@GetMapping("/getMemoryData")
	public QueryResult getMemoryData() throws MyRuntimeException {
		try {
			DashboardController.getMemoryDataApiCallCount.incrementAndGet();
			return service.getMemoryData();
		} catch (MyRuntimeException e) {
			throw e;
		}
	}
	// Cache가 얼마나 히트했는지 확인해 봄.
	@GetMapping("/getServiceCacheCount")
	public AtomicLong getServiceCacheCount() throws MyRuntimeException {
		DashboardController.getServiceCacheCountApiCallCount.incrementAndGet();
		return DashboardService.cacheHitCount;
	}
	
	//Api별 호출 횟수 확인.
	@GetMapping("/getApiCallCountList")
	public String getApiCallCountList() throws MyRuntimeException {
		DashboardController.getApiCallCountListApiCallCount.incrementAndGet();
		return new StringBuilder().append("getMemoryDataApiCallCount : ")
									.append(getMemoryDataApiCallCount)
									.append(" , getServiceCacheCountApiCallCount : ")
									.append(getServiceCacheCountApiCallCount)
									.append(" , getApiCallCountListApiCallCount : ")
									.append(getApiCallCountListApiCallCount).toString();
	}
}
