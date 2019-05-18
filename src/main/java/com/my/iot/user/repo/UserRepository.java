package com.my.iot.user.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.my.iot.user.connection.UserConnection;
import com.my.iot.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findBySocial(UserConnection userConnection);

}
