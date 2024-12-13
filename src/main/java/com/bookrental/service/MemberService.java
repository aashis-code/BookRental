package com.bookrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.MemberDto;

@Service
public interface MemberService {
	
	boolean memberOperation(MemberDto memberDto);
	
	MemberDto getMemberById(Integer memberId);
	
	List<MemberDto> getAllMembers();

	boolean deleteMember(Integer memberId);

}
