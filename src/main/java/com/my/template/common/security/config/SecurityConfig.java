package com.my.template.common.security.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CompositeFilter;

import com.my.template.common.security.filter.GoogleOAuth2ClientAuthenticationProcessingFilter;
import com.my.template.common.security.filter.JWTFilter;
import com.my.template.common.security.google.service.GoogleLoginService;
import com.my.template.common.security.service.UserTokenService;
import com.my.template.conf.ClientResources;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@EnableOAuth2Client
@AllArgsConstructor
/**
 * SecurityConfig.java
 * @author 효민영♥
 *
 */
// 보안에 관한 설정이 대부분 모여 있음.
// 일부 Bean정의도 섞여 있음. 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final OAuth2ClientContext oauth2ClientContext;
	private final GoogleLoginService service;
	private final UserTokenService userTokenService;

	// Security를 정의한다.
	// OAuth 2.0 기반 구글 로그인 방식을 정의한다.

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Cross-site Request forgery disable
		http.csrf().disable();

		// 대시보드 Page, Admin 기능 확인할 때 활용됨.
		// google Oauth 2.0 필터 맵핑 등록
		http.authorizeRequests().antMatchers("/", "/login/google").permitAll().anyRequest()
				.authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and()
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);

		http.logout().invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll();

	}

	// JWT 필터 빈 정의
	@Bean
	public FilterRegistrationBean<JWTFilter> tokenFilter() {
		FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
		JWTFilter jwtFilter = new JWTFilter();
		jwtFilter.setUserTokenService(userTokenService);

		// rest api 인증에 JWT토큰 인증 사용됨.
		registrationBean.setFilter(jwtFilter);
		registrationBean.addUrlPatterns("/api/*");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<Filter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		// OAuth2ClientContextFilter 등록
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	// 구글 리소스 전용 빈
	@Bean
	@ConfigurationProperties("google")
	public ClientResources google() {
		return new ClientResources();
	}

	// GoogleOAuth2ClientAuthenticationProcessingFilter 초기화
	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(google(), new GoogleOAuth2ClientAuthenticationProcessingFilter(service)));
		filter.setFilters(filters);
		return filter;
	}

	private Filter ssoFilter(ClientResources client, OAuth2ClientAuthenticationProcessingFilter filter) {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		filter.setRestTemplate(restTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(),
				client.getClient().getClientId());
		filter.setTokenServices(tokenServices);
		tokenServices.setRestTemplate(restTemplate);
		return filter;
	}
}
