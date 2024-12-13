package com.bookrental.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if(authentication == null || !authentication.isAuthenticated()) {
//			return Optional.empty();
//		}
//		return Optional.of(authentication.getName());

		return Optional.of("Aashis");
	}

}
