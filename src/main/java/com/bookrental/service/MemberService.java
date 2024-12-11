package com.bookrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.MemberDto;
import com.bookrental.model.Member;

@Service
public interface MemberService {
	
	boolean memberOperation(MemberDto memberDto);
	
	Member getMemberById(Integer memberId);
	
	List<Member> getAllMembers();

}
