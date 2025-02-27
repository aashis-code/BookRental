package com.bookrental.service;

import org.springframework.stereotype.Service;

import com.bookrental.model.Member;
import com.bookrental.security.LoginMemberDto;

public interface AuthenticationSevice {
	
	Member authenticate(LoginMemberDto input);

}
