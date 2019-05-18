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

    public User findBySocial(UserConnection userConnection) {
        final User user = userRepository.findBySocial(userConnection);
        if (user == null) throw new RuntimeException();
        return user;
    }

    public boolean existing(UserConnection userConnection) {
        final User user = userRepository.findBySocial(userConnection);
        return (user != null);
    }
}
