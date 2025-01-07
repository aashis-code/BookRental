package com.bookrental.service;

import com.bookrental.dto.MemberDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.helper.pagination.PaginationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
	
	boolean saveAndUpdateMember(MemberDto memberDto);
	
	MemberDto getMemberById(Integer memberId);
	
	List<MemberDto> getAllMembers();
	
	PaginatedResponse getPaginatedMemberList(PaginationRequest paginationRequest);

	void deleteMember(Integer memberId);
	
	boolean assignRoles(Integer memberId, List<String> roles);

}
