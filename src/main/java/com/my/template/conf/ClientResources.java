package com.my.template.conf;

/**
 * Google OAuth 2.0 인증 시 사용할 수 있도록 Property를 Bean으로 만들어 관리함.
 * 다른 성격의 모듈도 Property의 일부를 Bean으로 만들어 활용해야 하면, 이 영역에 정의함.
 */
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor (access = AccessLevel.PUBLIC)
@NoArgsConstructor (access = AccessLevel.PUBLIC)
@Getter (value = AccessLevel.PUBLIC)
@Setter (value = AccessLevel.PUBLIC)
public class ClientResources {

	//Google Client_id, Secret
    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    //Google의 userInfoUri 가 저장되는 리소스 
    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

}
