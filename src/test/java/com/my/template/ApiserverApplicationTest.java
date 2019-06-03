package com.my.template;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.my.template.student.Student;
import com.my.template.student.StudentMyBatisRepository;
import com.my.template.student.StudentQuerySupport;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiserverApplicationTest {
	
	@Autowired
	StudentMyBatisRepository repository;
	
	@Autowired
	StudentQuerySupport queryDslRepo;
	
	@Test
	public void test1() {
		repository.insert(new Student(10002L, "John", "A1234657"));
		repository.insert(new Student(10011L, "Brad", "A1234677"));
		List<Student> list = repository.findAll();
		assertEquals(list.size(),2);
	}
	
	@Test
	public void test2() {
		repository.insert(new Student(30L, "John", "A1234657"));
		repository.insert(new Student(40L, "Brad", "A1234677"));
		List<Student> list = queryDslRepo.findAll();
		assertEquals(list.size(),4);
	}

}
