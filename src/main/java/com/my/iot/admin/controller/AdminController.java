package com.my.iot.admin.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.iot.common.exception.MyRuntimeException;
import com.my.iot.dashboard.controller.DashboardController;
import com.my.iot.dashboard.service.DashboardService;
import com.my.iot.kafka.handller.KafkaMsgProducer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
@Getter
@Setter
@NoArgsConstructor
@RequestMapping("/admin")
/**
 * AdminController.java
 * @author 효민영♥
 *
 */
public class AdminController {

	@Autowired
	KafkaMsgProducer producer;

	// Kafka Producer 메세지 입력
	@GetMapping("/kafka/produceMsg")
	public JSONObject produceMsg(String msg) {
		try {
			if (msg == null)
				throw new MyRuntimeException();

			producer.sendToKafkaQueue("test", msg.getBytes());
			Map<String, Object> map = new HashMap<>();
			map.put("result", "Success");
			return new JSONObject(map);

		} catch (Exception e) {
			Map<String, Object> map = new HashMap<>();
			map.put("msg", e.getMessage());
			map.put("result", "Fail");

			return new JSONObject(map);
		}
	}

	// Dashboard Api Cache가 얼마나 히트했는지 확인해 봄.
	@GetMapping("/dashboard/getServiceCacheCount")
	public AtomicLong getServiceCacheCount() throws MyRuntimeException {
		DashboardController.getServiceCacheCountApiCallCount.incrementAndGet();
		return DashboardService.cacheHitCount;
	}

	// Dashboard Api Api별 호출 횟수 확인.
	@GetMapping("/dashboard/getApiCallCountList")
	public String getApiCallCountList() throws MyRuntimeException {
		DashboardController.getApiCallCountListApiCallCount.incrementAndGet();
		return new StringBuilder().append("getMemoryDataApiCallCount : ").append(DashboardController.getMemoryDataApiCallCount)
				.append(" , getServiceCacheCountApiCallCount : ").append(DashboardController.getServiceCacheCountApiCallCount)
				.append(" , getApiCallCountListApiCallCount : ").append(DashboardController.getApiCallCountListApiCallCount).toString();
	}
}
