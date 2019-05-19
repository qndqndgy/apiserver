package com.my.iot.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.my.iot.common.util.TerminateBean;

/**
 * Springboot App을 원격에서 종료할 수 있도록 기능을 오픈하기 위해 추가한 Config
 * (현재 사용하지 않아서, 제거해도 상관 없음)
 * @author 효민영♥
 *
 */
@Configuration
@ComponentScan(basePackages = "com.my.iot")
public class ShutdownConfig {

    @Bean
    public TerminateBean getTerminateBean() {
        return new TerminateBean();
    }
}