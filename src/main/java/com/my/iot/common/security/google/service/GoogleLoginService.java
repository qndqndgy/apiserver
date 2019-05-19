package com.my.iot.common.security.google.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.my.iot.common.security.google.model.GoogleLoginModel;
import com.my.iot.common.security.google.user.connection.UserConnection;
import com.my.iot.common.security.google.user.model.User;
import com.my.iot.common.security.google.user.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GoogleLoginService {

    private final UserService service;

    public UsernamePasswordAuthenticationToken authorize(UserConnection userConnection , GoogleLoginModel userDetails) {

        if (service.existing(userDetails.getEmail())) {
            // 기존 회원일 경우에는 데이터베이스에서 조회해서 인증 처리
            final User user = service.findByEmail(userDetails.getEmail());
            return setAuthenticationToken(user);
        } else {
            // 새 회원일 경우 회원가입 이후 인증 처리
            final User user = service.signUp(userConnection);
            return setAuthenticationToken(user);

        }
    }


    private UsernamePasswordAuthenticationToken setAuthenticationToken(Object user) {
        return new UsernamePasswordAuthenticationToken(user, null, getAuthorities("ROLE_USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

}
