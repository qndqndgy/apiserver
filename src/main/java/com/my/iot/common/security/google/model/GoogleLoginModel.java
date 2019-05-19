package com.my.iot.common.security.google.model;


import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.my.iot.common.security.google.user.connection.UserConnection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
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


    public void setAccessToken(OAuth2AccessToken accessToken) {
        this.access_token = accessToken.getValue();
        this.expiration = accessToken.getExpiration().getTime();
    }


}
