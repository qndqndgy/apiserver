package com.my.iot.dashboard.controller;

import org.influxdb.dto.QueryResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.iot.dashboard.test.InfluxDBTest;

@RestController
@RequestMapping("/api/v1")
public class DashboardController {
	
	@GetMapping("/test/getInfluxData")
	public QueryResult helloworld() {
		return InfluxDBTest.myTest();
	}
	
}
