package com.my.iot.common.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.iot.common.exception.AuthenticationException;
import com.my.iot.common.security.service.UserTokenService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
/**
 * JWTFilter.java
 * @author 효민영♥
 *
 * JWT 기반의 인증서비스를 제공하는 필터.
 * JWT는 암호화의 개념이 아닌, 단순 Encoding/Decoding이므로 서버에 인증서 설치 및 HTTPS 사용이 권장.
 */
public class JWTFilter implements Filter {

	private UserTokenService userTokenService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String header = req.getHeader("Authorization");

		try {
			// Header를 검증하여, 실패한 경우 Exception을 발생시킨다.
			if (header == null || !userTokenService.validateToken(header)) {
				AuthenticationException errorResponse = new AuthenticationException("Authentication Failed");
				response.getWriter().write(convertObjectToJson(errorResponse));
			}
		} catch (Exception e) {
			AuthenticationException errorResponse = new AuthenticationException("invalid token");
			response.getWriter().write(convertObjectToJson(errorResponse));
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		//Nothing to do
	}

	
	public String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
}
