package com.my.iot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.my.iot.common.util.WinProcessUtil;

/**
 * ApiserverApplication.java
 * Boot 시작 지점
 * InfluxDB, Telegraf등을 한꺼번에 올리려고 기능을 추가했었는데, 
 * 그렇게 올린 InfluxDB 프로세스가 불안정해서 (간헐적인 접속 안됨 현상) 사용 안함.
 * @author 효민영♥
 *
 */
@SpringBootApplication
public class ApiserverApplication implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	//Test 종료 후 제거 TODO
	/*
	 * @Autowired StudentMyBatisRepository repository;
	 */

	public static void main(String[] args) {
		SpringApplication.run(ApiserverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// 비즈니스 로직 실행을 위한, 초기화 단계
		// 불안정해서 로직 제거함..
	}

	@Deprecated
	private void prepareToRunServer(){
		// 서버 실행 준비
		
		logger.info("Trying InfluxDB Initialization");
		try {
			WinProcessUtil.startInfluxDProc();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Trying Telegraf Agent Initialization");
		try {
			WinProcessUtil.startTelegrafDProc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
