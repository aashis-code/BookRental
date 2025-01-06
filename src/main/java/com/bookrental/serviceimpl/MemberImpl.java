package com.bookrental.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookrental.dto.FilterRequest;
import com.bookrental.dto.MemberDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.model.Member;
import com.bookrental.model.Role;
import com.bookrental.repository.MemberRepo;
import com.bookrental.repository.RoleRepo;
import com.bookrental.service.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberImpl implements MemberService {

	private final MemberRepo memberRepo;

	private final PasswordEncoder passwordEncoder;

	private final RoleRepo roleRepo;

	@Override
	@Transactional
	public boolean saveAndUpdateMember(MemberDto memberDto) {
		Member member;
		if (memberDto.getId() != null) {
			member = memberRepo.findByIdAndDeleted(memberDto.getId(), Boolean.FALSE)
					.orElseThrow(() -> new ResourceNotFoundException("MemberId", String.valueOf(memberDto.getId())));
			CoustomBeanUtils.copyNonNullProperties(memberDto, member);
			memberRepo.save(member);
		} else {
			Role role;
			member = new Member();
			CoustomBeanUtils.copyNonNullProperties(memberDto, member);
			Optional<Role> roleByName = roleRepo.findByName("NORMAL");
			if(roleByName.isEmpty()) {
				role = Role.builder()
				.name("NORMAL")
				.description("This is basic level role assigned for ever member.")
				.build();
				roleRepo.save(role);
				member.setRoles(List.of(role));
				return true;
			} 
			member.setRoles(List.of(roleByName.get()));
			member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
			memberRepo.save(member);
		}
		return true;
	}

	@Override
	public MemberDto getMemberById(Integer memberId) {
		if (memberId < 1) {
			throw new ResourceNotFoundException("Please, provide valid member ID.", null);
		}
		MemberDto memberDto = new MemberDto();
		Member member = memberRepo.findByIdAndDeleted(memberId, Boolean.FALSE)
				.orElseThrow(() -> new ResourceNotFoundException("Member Id", String.valueOf(memberId)));
		CoustomBeanUtils.copyNonNullProperties(member, memberDto);
		memberDto.setRoles(member.getRoles().stream().map(Role::getName).toList());
		return memberDto;
	}

	@Override
	public List<MemberDto> getAllMembers() {
		return memberRepo.findAllMember(Boolean.FALSE).stream().map(member -> {
			MemberDto memberDto = new MemberDto();
			CoustomBeanUtils.copyNonNullProperties(member, memberDto);
			memberDto.setRoles(member.getRoles().stream().map(Role::getName).toList());
			return memberDto;
		}).toList();
	}

	@Override
	public PaginatedResponse getPaginatedMemberList(FilterRequest filterRequest) {
		Sort sort = Sort.by(Sort.Direction.fromString(filterRequest.getSortDir()),filterRequest.getOrderBy());
		Pageable pageable = PageRequest.of(filterRequest.getPageNumber(), filterRequest.getPageSize(), sort);
		Page<Map<String, Object>> response = memberRepo.filterMemberPaginated(filterRequest.getKeyword(), filterRequest.getStartDate(), filterRequest.getEndDate(), Boolean.FALSE, pageable);
		return PaginatedResponse.builder().content(response.getContent())
				.totalElements(response.getTotalElements()).currentPageIndex(response.getNumber())
				.numberOfElements(response.getNumberOfElements()).totalPages(response.getTotalPages()).build();

	}

	@Override
	public void deleteMember(Integer memberId) {
		if (memberId < 1) {
			throw new ResourceNotFoundException("Please, provide valid member ID.", null);
		}
		 int result = memberRepo.deleteMemberById(memberId);
		 if(result<1) {
			 throw new ResourceNotFoundException("MemberId", String.valueOf(memberId));
		 }
	}

	@Override
	public boolean assignRoles(Integer memberId, List<String> roles) {
		if (memberId < 1) {
			throw new ResourceNotFoundException("Please, provide valid member ID.", null);
		}
		Member member = this.memberRepo.findByIdAndDeleted(memberId, Boolean.FALSE)
				.orElseThrow(() -> new ResourceNotFoundException("Member Id", String.valueOf(memberId)));
		List<String> roleNames = member.getRoles().stream().map(Role::getName).toList();
		List<Role> listOfRoles = new ArrayList<Role>();
		for (String role : roles) {
			String upperCaseRole = role.trim().toUpperCase();
			if (!roleNames.contains(upperCaseRole)) {
				Role roleEntity = roleRepo.findByName(upperCaseRole)
						.orElseThrow(() -> new ResourceNotFoundException("Role", upperCaseRole));
				listOfRoles.add(roleEntity);
			}
		}
		if (!listOfRoles.isEmpty()) {
			member.setRoles(listOfRoles);
			memberRepo.save(member);
		}
		return true;
	}

}
