package com.my.iot.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 코드기반 Api 문서화.
 * @author 효민영♥
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	//Swagger 메뉴얼 접속 주소 : https://localhost:7443/swagger-ui.html
	// /login/google 에서 Google 로그인 먼저 해야 함.
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
