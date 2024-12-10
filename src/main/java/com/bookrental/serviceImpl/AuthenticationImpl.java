package com.bookrental.serviceImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.bookrental.model.Member;
import com.bookrental.repository.MemberRepo;
import com.bookrental.security.LoginMemberDto;
import com.bookrental.service.AuthenticationSevice;

@Component
public class AuthenticationImpl implements AuthenticationSevice{

	private final MemberRepo memberRepo;
	private final AuthenticationManager authenticationManager;

	public AuthenticationImpl(MemberRepo memberRepo, AuthenticationManager authenticationManager) {
		this.memberRepo = memberRepo;
		this.authenticationManager = authenticationManager;
	}

	public Member authenticate(LoginMemberDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return memberRepo.findByEmail(input.getEmail()).orElseThrow();
	}

}
