package com.bookrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.FilterRequest;
import com.bookrental.dto.MemberDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.model.Member;

@Service
public interface MemberService {
	
	boolean saveAndUpdateMember(MemberDto memberDto);
	
	MemberDto getMemberById(Integer memberId);
	
	List<MemberDto> getAllMembers();
	
	PaginatedResponse getPaginatedMemberList(FilterRequest filterRequest);

	void deleteMember(Integer memberId);
	
	boolean assignRoles(Integer memberId, List<String> roles);

}
