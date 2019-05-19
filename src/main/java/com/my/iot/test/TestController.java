package com.my.iot.test;

import org.influxdb.dto.QueryResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.iot.common.exception.MyRuntimeException;
import com.my.iot.common.influxdb.InfluxDBTest;
import com.my.iot.common.util.WinProcessUtil;

import lombok.AllArgsConstructor;

/**
 * TestController.java
 * 테스트용으로 임시로 사용했던 클래스
 * @author 효민영♥
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {

	//Test 용
	@GetMapping("/hello")
	public String helloworld() {
		return "hello world";
	}
	
	//현재 안 씀
	@Deprecated
	@GetMapping("/destroyAllProcs")
	public String terminateRunningProcs() {
		WinProcessUtil.shutdownAllProcsGracefully();
		return "Success";
	}
	
	// Test용 Data지만, 크게 다르지 않음..
	@GetMapping("/getInfluxData")
	public QueryResult getInfluxData() throws MyRuntimeException {
		try {
			return InfluxDBTest.myTest2();
		} catch (MyRuntimeException e) {
			throw e;
		}
	}
}
