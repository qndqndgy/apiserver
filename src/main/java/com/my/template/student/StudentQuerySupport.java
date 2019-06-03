package com.my.template.student;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.my.template.student.QStudent;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * StudentQuerySupport.java
 * @author 효민영♥
 *
 */
// JPA QueryDsl Test 용 클래스
// QueryDsl 역시 가독성이 그리 좋지 않은듯 함.
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
