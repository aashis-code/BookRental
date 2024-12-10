package com.bookrental.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginMemberDto {
	
	private String email;
	
	private String password;

}
