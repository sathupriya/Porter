package com.example.Porter.Responsehandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GlobalLoggingFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

		String method = request.getMethod();
		String uri = request.getRequestURI();
		String query = request.getQueryString();

		try {
			log.info("Incoming Request: [{}] {} {}", method, uri, (query != null ? "?" + query : ""));
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		} finally {
			String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
			if (!requestBody.isBlank()) {
				log.info("Request Body: {}", requestBody);
			}

			String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
			log.info("response [{}] {} - Status: {}", method, uri, response.getStatus());
			log.info("Response Body: {}", responseBody);

			wrappedResponse.copyBodyToResponse();
		}
	}

}
