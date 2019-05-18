package com.my.iot.common.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.iot.common.security.service.UserTokenService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class JWTFilter implements Filter {

	@Autowired
    private UserTokenService userTokenService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader("Authorization");

        try {
            if(!userTokenService.validateToken(header)) {
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

    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
