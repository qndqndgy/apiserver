package com.my.iot;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.my.iot.common.util.WinProcessUtil;
import com.my.iot.student.Student;
import com.my.iot.student.StudentMyBatisRepository;

@SpringBootApplication
public class ApiserverApplication implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	//Test 종료 후 제거 TODO
	@Autowired
	StudentMyBatisRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ApiserverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info("Student id 10001 -> {}", repository.findById(10001L));

		logger.info("Inserting -> {}", repository.insert(new Student(10010L, "John", "A1234657")));
		logger.info("Inserting -> {}", repository.insert(new Student(10011L, "Brad", "A1234677")));
		logger.info("Inserting -> {}", repository.insert(new Student(10012L, "Chloe", "A1234688")));
		logger.info("Inserting -> {}", repository.insert(new Student(10013L, "Lumi", "A1234699")));
		logger.info("Update 10003 -> {}", repository.update(new Student(10001L, "Name-Updated", "New-Passport")));

//		repository.deleteById(10002L);

		logger.info("All users -> {}", repository.findAll());
		
		// 비즈니스 로직 실행을 위한, 초기화 단계
		// 불안정해서 로직 제거함..
//		prepareToRunServer();
	}

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
