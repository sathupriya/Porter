package com.example.Porter.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.CustomerDto;
import com.example.Porter.OAuth.CustomerOAuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//	private final JwtCookieFilter jwtCookieFilter;
//	
//	public SecurityConfig(JwtCookieFilter jwtCookieFilter) {
//		this.jwtCookieFilter = jwtCookieFilter;
//	}

	private final JwtTokenFilter jwtTokenfilter;
	private final CustomerOAuthService service;

	public SecurityConfig(JwtTokenFilter jwtTokenfilter, CustomerOAuthService service) {
		this.jwtTokenfilter = jwtTokenfilter;
		this.service = service;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

// for role based security
//		http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//		.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated()).addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/location/**", "/customer/addCustomer", "/customer/customerLogin",
								"/oauth2/**", "/oauthLogin/**", "/auth/**","/oauth/oauth-success","/oauth/oauth-success?token=").permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2Login(oauth -> oauth.successHandler((request, response, authentication) -> {
					OAuth2User principal = (OAuth2User) authentication.getPrincipal();
					ResponseEntity<APIResponseDto<CustomerDto>> dto = service.oauthLogin(principal, request);

//					response.setContentType("application/json");
//					response.setCharacterEncoding("UTF-8");
//					String json = new ObjectMapper()
//							.writeValueAsString(SuccessResponseHandler.success(dto, "Google login success"));
//					response.getWriter().write(json);
					
					String token = dto.getBody().getData().getJwtToken();
					response.sendRedirect("/oauth/oauth-success?token=" + token);

				}))
				.addFilterBefore(jwtTokenfilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
