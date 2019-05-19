package com.my.iot.common.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.my.iot.common.security.google.model.GoogleLoginModel;
import com.my.iot.common.security.google.service.GoogleLoginService;
import com.my.iot.common.security.google.user.connection.UserConnection;

/**
 * GoogleOAuth2ClientAuthenticationProcessingFilter.java
 * 
 * 구글 로그인 OAuth 2.0를 사용하기 위해 구현함.
 * @author 효민영♥
 *
 */
public class GoogleOAuth2ClientAuthenticationProcessingFilter extends OAuth2ClientAuthenticationProcessingFilter {

	// 원래 ObjectMapper는 쓰지 않는데, 참고해서 가져온 소스로 그냥 유지함.
	private ObjectMapper mapper = new ObjectMapper();
	private GoogleLoginService service;

	public GoogleOAuth2ClientAuthenticationProcessingFilter(GoogleLoginService service) {
		super("/login/google");
		this.service = service;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// 일반 OAuth2 Filter와 동일함.
		super.doFilter(req, res, chain);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		// Successful일 경우에 추가조치만 해주면 된다.
		final OAuth2AccessToken accessToken = restTemplate.getAccessToken();
		final OAuth2Authentication auth = (OAuth2Authentication) authResult;
		final Object details = auth.getUserAuthentication().getDetails();

		final GoogleLoginModel userDetails = mapper.convertValue(details, GoogleLoginModel.class);
		userDetails.setAccessToken(accessToken);
		final UserConnection userConnection = UserConnection.valueOf(userDetails);

		final UsernamePasswordAuthenticationToken authenticationToken = service.authorize(userConnection, userDetails);
		super.successfulAuthentication(request, response, chain, authenticationToken);

	}

}
