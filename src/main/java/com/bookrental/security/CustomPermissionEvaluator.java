package com.bookrental.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.bookrental.model.Member;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator{

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		
		if(targetDomainObject instanceof Member) {
			Member member =	(Member) targetDomainObject;
			String email = authentication.getName();
			return member.getEmail().equals(email) && permission.equals("ACCESS");
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		return false;
	}

}
