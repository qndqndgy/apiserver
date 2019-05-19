package com.my.iot.common.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/**
 * WebConfig.java
 * @author 효민영♥
 *
 * CORS 차단옵션 풀려고, 추가하였는데 결국 CORS가 발생하지 않도록 프로젝트를 구성했기 때문에
 * 필요 없어 졌다.
 */
public class WebConfig implements WebMvcConfigurer {
    @Override
    // 후에 보안 강화할 때, 제거 필요.
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    }
}