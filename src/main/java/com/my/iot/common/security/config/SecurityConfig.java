package com.my.iot.common.security.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.my.iot.common.security.filter.GoogleOAuth2ClientAuthenticationProcessingFilter;
import com.my.iot.common.security.filter.JWTFilter;
import com.my.iot.common.security.google.service.GoogleLoginService;
import com.my.iot.conf.ClientResources;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@EnableOAuth2Client
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2ClientContext oauth2ClientContext;
    private final GoogleLoginService service;

    // Security를 정의한다.
    // OAuth 2.0 기반 구글 로그인 방식을 정의한다.
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        http.csrf().disable();

        // 이 부분은, 분석 필요
        // MY TODO
		/*
		 * http.antMatcher("/**").authorizeRequests().antMatchers("/",
		 * "/login**").permitAll().anyRequest()
		 * .authenticated().and().exceptionHandling() .authenticationEntryPoint(new
		 * LoginUrlAuthenticationEntryPoint("/")).and() .addFilterBefore(ssoFilter(),
		 * BasicAuthenticationFilter.class);
		 * 
		 * http.logout() .invalidateHttpSession(true) .clearAuthentication(true)
		 * .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		 * .logoutSuccessUrl("/") .permitAll();
		 */
        

    }
    
    @Bean
    public FilterRegistrationBean<JWTFilter> tokenFilter() {
        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
        JWTFilter jwtFilter = new JWTFilter();

        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
    
    @Bean
    public FilterRegistrationBean<Filter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
    	// Filter Bean 정의
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    // 구글 설정
    @Bean
    @ConfigurationProperties("google")
    public ClientResources google() {
        return new ClientResources();
    }
    
    // GoogleOAuth2ClientAuthenticationProcessingFilter 초기화
    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        //Filters의 의미는 무엇인지?
        // MY TODO
        filters.add(ssoFilter(google(), new GoogleOAuth2ClientAuthenticationProcessingFilter(service)));
        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(ClientResources client, OAuth2ClientAuthenticationProcessingFilter filter) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setRestTemplate(restTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
        filter.setTokenServices(tokenServices);
        tokenServices.setRestTemplate(restTemplate);
        return filter;
    }
}
