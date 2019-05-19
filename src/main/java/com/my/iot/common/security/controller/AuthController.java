package com.my.iot.common.security.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.iot.common.security.domain.UserSignInInfo;
import com.my.iot.common.security.service.UserTokenService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    UserTokenService userTokenService;

    @PostMapping("/register")
    public JSONObject registerUserInfo(@RequestBody UserSignInInfo userSignInInfo) {
        return userTokenService.registerUser(userSignInInfo.getUsername(),
                userSignInInfo.getPassword());
    }

    @GetMapping("/signIn")
    public JSONObject loginUser(@RequestBody UserSignInInfo userSignInInfo) {
        return userTokenService.getUserTokenByLogin(userSignInInfo.getUsername(),
                userSignInInfo.getPassword());
    }

    @PutMapping("/refreshToken")
    public JSONObject refreshToken(@RequestHeader String authorization) {
        return userTokenService.updateTokenGenCount(authorization);
    }

}
