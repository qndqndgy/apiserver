package com.my.template.common.security.repo;

import org.springframework.data.repository.CrudRepository;

import com.my.template.common.security.domain.UserToken;

/**
 * UserTokenRepository.java
 * @author 효민영♥
 *
 */
public interface UserTokenRepository extends CrudRepository<UserToken, Long> {

    UserToken findByUsername(String username);
}
