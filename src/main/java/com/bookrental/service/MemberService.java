package com.bookrental.service;

import org.springframework.stereotype.Service;

import com.bookrental.dto.MemberDto;

@Service
public interface MemberService {
	
	boolean memberOperation(MemberDto memberDto);

}
