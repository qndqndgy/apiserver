package com.my.template.student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


/**
 * 테스트용 Student JPA 엔티티
 * @author 효민영♥
 *
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@Column(nullable = false)
	private String name;
	
	@NonNull
	@Column(nullable = false)
	private String passport;

	@Override
	public String toString() {
		return String.format("Student [id=%s, name=%s, passport=%s]", id, name, passport);
	}

}
