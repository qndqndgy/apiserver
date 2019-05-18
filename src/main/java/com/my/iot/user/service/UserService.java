package com.my.iot.user.service;

import org.springframework.stereotype.Service;

import com.my.iot.user.connection.UserConnection;
import com.my.iot.user.model.User;
import com.my.iot.user.repo.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User signUp(UserConnection userConnection) {
        final User user = User.signUp(userConnection);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        final User user = userRepository.findByEmail(email);
        if (user == null) throw new RuntimeException();
        return user;
    }

    // User 최초 로그인인지 H2 DB 조회한다.
    public boolean existing(String email) {
        final User user = userRepository.findByEmail(email);
        return (user != null);
    }
}
