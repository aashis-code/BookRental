package com.bookrental.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponse {

	private String token;
	
	private Long expiresIn;
	
}
