package com.my.template.common.security.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
/**
 * UserToken.java
 * @author 효민영♥
 * 
 * JWT 쿠키 생성/검증에 상요되는 클래스.
 * 직접 구현하지 않고, 참고하여 가져다 사용함.
 *
 * 자세한 테스트는 생략
 */
public class UserToken {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String token;

    private int count;

    private static Map<String, Object> jwtMap = new HashMap<>();
    static {
        jwtMap.put("alg", "HS256");
        jwtMap.put("typ", "JWT");
    }

    //Server전용 secret
    private static final String JWT_SECRET = "yukinim";

    public static UserToken getInstance(String userName, String password) {
        UserToken userToken = new UserToken();
        userToken.username = userName;
        userToken.password = password;
        userToken.count = 1;
        userToken.token = userToken.makeSignInToken();

        return userToken;
    }

    public static String getUserNameByToken(String token) {
        return JWT.decode(token).getClaim("username").asString();
    }

    public String makeSignInToken() {
        return JWT.create()
                .withHeader(jwtMap)
                .withClaim("username", username)
                .withClaim("count", count)
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    public boolean verifyToken(String token) {
        int count = JWT.decode(token).getClaim("count").asInt();

        if(count != this.count) {
            return false;
        }

        try {
            JWT.require(Algorithm.HMAC512(JWT_SECRET))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public String addCountAndGetToken() {
        count += 1;
        return makeSignInToken();
    }

    public void setToken(String token) {
        this.token = token;
    }
}
