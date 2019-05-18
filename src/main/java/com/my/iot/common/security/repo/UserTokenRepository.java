package com.my.iot.common.security.repo;

import org.springframework.data.repository.CrudRepository;

import com.my.iot.common.security.domain.UserToken;

public interface UserTokenRepository extends CrudRepository<UserToken, Long> {

    UserToken findByUsername(String username);
}
