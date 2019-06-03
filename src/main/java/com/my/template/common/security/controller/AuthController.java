package com.my.template.common.security.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.template.common.security.domain.UserSignInInfo;
import com.my.template.common.security.service.UserTokenService;

import lombok.AllArgsConstructor;

/**
 * AuthController.java
 * @author 효민영♥
 *
 * 권한관련 최초 등록 요청할 때 사용되는 api로, 필터 맵핑에서도 제외된다.
 * 
 */
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	UserTokenService userTokenService;

	//register -> username, password를 전달받아 HS256기반으로 JWT쿠키를 생성해 리턴함.
	@PostMapping("/register")
	public JSONObject registerUserInfo(@RequestBody UserSignInInfo userSignInInfo) {
		return userTokenService.registerUser(userSignInInfo.getUsername(), userSignInInfo.getPassword());
	}
	
	
	// 로그인 기능처럼 쓰려고 했으나, 필요없음.
	@Deprecated
	@GetMapping("/signIn")
	public JSONObject loginUser(@RequestBody UserSignInInfo userSignInInfo) {
		return userTokenService.getUserTokenByLogin(userSignInInfo.getUsername(), userSignInInfo.getPassword());
	}

}
