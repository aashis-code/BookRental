package com.bookrental.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.ResponseObject;
import com.bookrental.security.JwtService;
import com.bookrental.security.LoginMemberDto;
import com.bookrental.security.LoginResponse;

@RequestMapping("/auth")
//@RestController
public class AuthenticationController {

	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	public AuthenticationController(JwtService jwtService, AuthenticationManager authenticationManager) {
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

//    Log In user
	@PostMapping("/login")
	public ResponseObject authenticate(@RequestBody LoginMemberDto loginMemberDto) {

		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginMemberDto.getEmail(), loginMemberDto.getPassword()));

		if (authenticate.isAuthenticated()) {
			String jwtToken = jwtService.generateToken(loginMemberDto.getEmail());
			LoginResponse loginResponse = LoginResponse.builder().token(jwtToken)
					.expiresIn(jwtService.getExpirationTime()).build();
			return new ResponseObject(true, "", loginResponse);
		} else {
			throw new ResourceNotFoundException("", null);
		}

	}

}
