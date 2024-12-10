package com.bookrental.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.ResponseObject;
import com.bookrental.repository.MemberRepo;
import com.bookrental.security.JwtService;
import com.bookrental.security.LoginMemberDto;
import com.bookrental.security.LoginResponse;
import com.bookrental.service.AuthenticationSevice;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	private final JwtService jwtService;

	private final AuthenticationSevice authenticationService;

	private final MemberRepo memberRepo;

	private final AuthenticationManager authenticationManager;

	public AuthenticationController(JwtService jwtService, AuthenticationSevice authenticationService,
			AuthenticationManager authenticationManager, MemberRepo memberRepo) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
		this.authenticationManager = authenticationManager;
		this.memberRepo = memberRepo;
	}

////    Registering User
//    @PostMapping("/signup")
//    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
//        User registeredUser = authenticationService.signup(registerUserDto);
//
//        return ResponseEntity.ok(registeredUser);
//    }

//    Log In user
	@PostMapping("/login")
	public ResponseObject authenticate(@RequestBody LoginMemberDto loginMemberDto) {
//		Member authenticatedUser = authenticationService.authenticate(loginMemberDto);

		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginMemberDto.getEmail(), loginMemberDto.getPassword()));

		if (authenticate.isAuthenticated()) {
//			user object fetch
			String jwtToken = jwtService.generateToken(loginMemberDto.getEmail());
			LoginResponse loginResponse = LoginResponse.builder().token(jwtToken)
					.expiresIn(jwtService.getExpirationTime()).build();
			return new ResponseObject(true, "", loginResponse);
		} else {
			throw new ResourceNotFoundException("", null);
		}

	}

}
