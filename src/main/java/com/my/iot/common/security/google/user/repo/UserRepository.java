package com.my.iot.common.security.google.user.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.my.iot.common.security.google.user.connection.UserConnection;
import com.my.iot.common.security.google.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
