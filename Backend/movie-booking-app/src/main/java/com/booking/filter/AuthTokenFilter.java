package com.booking.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthTokenFilter extends GenericFilterBean {

	@Value("${taskmanager.app.jwtSecret}")
	private String jwtSecret;

	@Value("${taskmanager.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) request;

		//HttpServletResponse httpRes = (HttpServletResponse) response;

		String authHeader = httpReq.getHeader("authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new ServletException("Missing or invalid authentication header");
		}

		String jwtToken = authHeader.substring(7);
		Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(jwtToken).getBody();

		httpReq.setAttribute("username", claims);
		chain.doFilter(request, response);
	}

}
