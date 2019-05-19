package com.my.iot.common.security.service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.my.iot.common.exception.AuthenticationException;
import com.my.iot.common.security.domain.UserToken;
import com.my.iot.common.security.repo.UserTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
/**
 * UserTokenService.java
 * @author 효민영♥
 * 
 * JWT인증을 위해 Token을 DB에 저장하거나, Select 기능을 제공하는 서비스 객체
 */
public class UserTokenService {

    UserTokenRepository userTokenRepository;

    public UserToken saveOrUpdate(UserToken userToken) {
        return userTokenRepository.save(userToken);
    }
    
    @SuppressWarnings("unchecked")
    // 새로운 User 및 토큰 등록.
	public JSONObject registerUser(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        UserToken info = getUserTokenByUserName(username);
        if(info != null) {
            jsonObject.put("result", info.getToken());
            return jsonObject;
        }

        UserToken userToken = UserToken.getInstance(username, password);
        saveOrUpdate(userToken);
        jsonObject.put("result", userToken.getToken());
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    // Token 카운팅을 한다. (보통은 발급 횟수 제한을 두기 때문)
	public JSONObject updateTokenGenCount(String token) {
        JSONObject jsonObject = new JSONObject();
        String username = UserToken.getUserNameByToken(token);
        UserToken userToken = getUserTokenByUserName(username);

        String newToken = userToken.addCountAndGetToken();
        userToken.setToken(newToken);
        saveOrUpdate(userToken);

        jsonObject.put("result", newToken);

        return jsonObject;
    }

    private UserToken getUserTokenByUserName(String username) {
        return userTokenRepository.findByUsername(username);
    }

    
    @SuppressWarnings("unchecked")
	public JSONObject getUserTokenByLogin(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        UserToken userToken = getUserTokenByUserName(username);

        if(userToken == null) {
            jsonObject.put("result", "No token for " + username);
        }

        if(!password.equals(userToken.getPassword())) {
            jsonObject.put("result", "invalid password");
        }
        jsonObject.put("result", userToken.getToken());
        return jsonObject;
    }

    // Request객체에서 뽑은 토큰을 DB에 있는 토큰과 비교하여 검증함.
    public boolean validateToken(String token) {
        String username;
        UserToken userToken;
        try {
            username = UserToken.getUserNameByToken(token);
            userToken = getUserTokenByUserName(username);
        } catch (Exception e) {
            throw new AuthenticationException("Authentication Failed");
        }

        return userToken.verifyToken(token);
    }

}
