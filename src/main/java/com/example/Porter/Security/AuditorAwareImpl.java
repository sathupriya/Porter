package com.example.Porter.Security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor(){
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication != null && authentication.isAuthenticated()) {
				return Optional.ofNullable(authentication.getName());
			}
		}catch(Exception e) {
			return Optional.of("System");
		}
		return Optional.of("System");
	}
}
