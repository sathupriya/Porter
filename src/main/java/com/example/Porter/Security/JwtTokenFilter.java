package com.example.Porter.Security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	public JwtTokenFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String path = request.getServletPath();

		if (path.startsWith("/customer/addCustomer") || path.startsWith("/customer/customerLogin")
				|| path.startsWith("/location/") || (path.startsWith("/oauth/oauth-success"))) {
			chain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Access token is missing or malformed");
			return;
		}

		String token = authHeader.substring(7);
		if (!jwtService.validateToken(token)) {
			setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Access Token is invalid or expired");
			return;
		}

		UsernamePasswordAuthenticationToken auth = jwtService.getAuthentication(token);
		SecurityContextHolder.getContext().setAuthentication(auth);

		chain.doFilter(request, response);

	}

	private void setErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
		response.setStatus(status);
		response.setContentType("application/json");
		String json = String.format("{\"success\":false,\"message\":\"%s\",\"data\":null}", message);
		response.getWriter().write(json);
	}
}
