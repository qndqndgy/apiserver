package com.my.iot.dashboard.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.iot.common.exception.MyRuntimeException;
import com.my.iot.dashboard.service.DashboardService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Vue대시보드에서 호출하는 Endpoint들을 모아놓은 RestController
 * @author 효민영♥
 *
 */
@Getter @Setter
@NoArgsConstructor
@RestController
@RequestMapping("/api/dashboard/v1")
public class DashboardController {
	
	@Autowired
	DashboardService service;
	
	// 한번씩 api별로 몇 번씩 호출됐는지 보려고 추가 해 놓음.
	// /admin에서 api 제공함. (admin.controller)
	public static AtomicLong getMemoryDataApiCallCount = new AtomicLong(0);
	public static AtomicLong getServiceCacheCountApiCallCount = new AtomicLong(0);
	public static AtomicLong getApiCallCountListApiCallCount = new AtomicLong(0);
	
	@GetMapping("/getMemoryData")
	public QueryResult getMemoryData() throws MyRuntimeException {
		try {
			DashboardController.getMemoryDataApiCallCount.incrementAndGet();
			return service.getMemoryData();
		} catch (MyRuntimeException e) {
			throw e;
		}
	}
}
