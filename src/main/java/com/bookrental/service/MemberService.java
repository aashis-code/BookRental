package com.bookrental.service;

import com.bookrental.dto.MemberDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.helper.pagination.PaginationRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MemberService {
	
	boolean saveAndUpdateMember(MemberDto memberDto);
	
	MemberDto getMemberById(Integer memberId);
	
	List<MemberDto> getAllMembers();
	
	PaginatedResponse getPaginatedMemberList(PaginationRequest paginationRequest);

	void deleteMember(Integer memberId);
	
	boolean assignRoles(Integer memberId, List<String> roles);

}
