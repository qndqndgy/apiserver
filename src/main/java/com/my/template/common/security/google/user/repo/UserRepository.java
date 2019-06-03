package com.my.template.common.security.google.user.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.my.template.common.security.google.user.model.User;

/**
 * UserRepository.java
 * @author 효민영♥
 *
 * Jpa repository로, Email로 사용자 조회하는 쿼리 하나만 존재.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
