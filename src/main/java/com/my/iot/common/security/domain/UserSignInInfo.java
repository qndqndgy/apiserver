package com.my.iot.common.security.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignInInfo {
    private String username;
    private String password;
}
