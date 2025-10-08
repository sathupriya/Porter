package com.example.Porter.Security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtCookieFilter extends OncePerRequestFilter {

	@Autowired
	JwtService jwtService;
	
	private static final Logger log = LoggerFactory.getLogger(JwtCookieFilter.class);


	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
	
		String token = Arrays.stream(Optional.ofNullable(req.getCookies()).orElse(new Cookie[0]))
				.filter(c -> "jwt".equals(c.getName())).findFirst().map(Cookie::getValue).orElse(null);
		if (token != null) {
		    try {
		        if (jwtService.validateToken(token)) {
		            UsernamePasswordAuthenticationToken auth = jwtService.getAuthentication(token);
		            SecurityContextHolder.getContext().setAuthentication(auth);
		        }
		    } catch (Exception ex) {
		    	log.warn("JWT validation failed: {}", ex.getMessage());
		    }
		}


		chain.doFilter(req, res);
	}
}
