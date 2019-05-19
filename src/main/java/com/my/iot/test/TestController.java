package com.my.iot.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.iot.common.util.WinProcessUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {

	@GetMapping("/hello")
	public String helloworld() {
		return "hello world";
	}
	
	@GetMapping("/destroyAllProcs")
	public String terminateRunningProcs() {
		WinProcessUtil.shutdownAllProcsGracefully();
		return "Success";
	}
}
