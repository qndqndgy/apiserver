package com.my.iot;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.my.iot.student.Student;
import com.my.iot.student.StudentMyBatisRepository;
import com.my.iot.student.StudentQuerySupport;
import com.querydsl.core.Tuple;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiserverApplicationTests {
	
	@Autowired
	StudentMyBatisRepository repository;
	
	@Autowired
	StudentQuerySupport queryDslRepo;
	
	@Test
	public void test1() {
		System.out.println("Hello Test!!");
		List<Student> list = repository.findAll();
		
		System.out.println(list.size());
		
		Student john = repository.findById(10010L);
		
		System.out.println(john.toString());
	}
	
	
	
	
	@Test
	public void test2() {
		System.out.println("Test (2) Start ...");
		List<Student> list = queryDslRepo.findAll();
		System.out.println(list.size());
		for(Student st : list) {
			System.out.println(st);
		}
	}

}
