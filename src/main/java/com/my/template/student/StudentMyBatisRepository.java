package com.my.template.student;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * StudentMyBatisRepository.java
 * @author 효민영♥
 *
 */
// Mybatis test 용 클래스
// Pure한 SQL을 쓰고싶을 땐, Mybatis로 활용하기 위해 일단 테스트 함.
@Mapper
public interface StudentMyBatisRepository {
	
	@Select("select * from student")
	public List<Student> findAll();

	@Select("SELECT * FROM student WHERE id = #{id}")
	public Student findById(long id);

	@Delete("DELETE FROM student WHERE id = #{id}")
	public int deleteById(long id);

	@Insert("INSERT INTO student(id, name, passport) VALUES (#{id}, #{name}, #{passport})")
	public int insert(Student student);

	@Update("Update student set name=#{name}, passport=#{passport} where id=#{id}")
	public int update(Student student);

}
