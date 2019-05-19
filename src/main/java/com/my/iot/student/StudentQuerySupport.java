package com.my.iot.student;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

// JPA QueryDsl Test 용 클래스
@Repository
public class StudentQuerySupport extends QuerydslRepositorySupport {

	private final JPAQueryFactory jpaQueryFactory;

	public StudentQuerySupport(JPAQueryFactory jpaQueryFactory) {
		super(Student.class);
		this.jpaQueryFactory = jpaQueryFactory;
	}

	public List<Student> findAll() {
		QStudent student = QStudent.student;
		return jpaQueryFactory.selectFrom(student).fetch();
	}
}
