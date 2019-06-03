package com.my.template.common.security.google.model;


import org.springframework.security.oauth2.common.OAuth2AccessToken;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
/**
 * GoogleLoginModel.java
 * @author 효민영♥
 * OAuth 2.0 기반으로, 구글에서 로그인하고 돌아오면 Filter에서 넣어주는 Login 정보.
 * 실제 Google에서 전달받는 user 정보가 입력된다.
 * 이메일, 프로필사진, 성별, 지역, 이름 등..
 */
public class GoogleLoginModel {

    private String sub;
    private String name;
    private String given_name;
    private String family_name;
    private String profile;
    private String picture;
    private String email;
    private boolean email_verified;
    private String gender;
    private String locale;

    private long expiration;
    private String access_token;

    // Token을 갖고 있어야, 필터에서 체크해서 다시 login 주소로 redirect 시키지 않는다.
    public void setAccessToken(OAuth2AccessToken accessToken) {
        this.access_token = accessToken.getValue();
        this.expiration = accessToken.getExpiration().getTime();
    }


}
