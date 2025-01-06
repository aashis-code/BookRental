package com.bookrental.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.bookrental.model.Member;
import com.bookrental.repository.MemberRepo;
import com.bookrental.security.LoginMemberDto;
import com.bookrental.service.AuthenticationSevice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationSevice{

	private final MemberRepo memberRepo;
	private final AuthenticationManager authenticationManager;

	public Member authenticate(LoginMemberDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return memberRepo.findByEmail(input.getEmail()).orElseThrow();
	}

}
