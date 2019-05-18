package com.my.iot.common.util;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerminateBean {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PreDestroy
	public void onDestroy() throws Exception {
		WinProcessUtil.shutdownAllProcessesGracefully();
	}
}
