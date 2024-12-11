package com.bookrental.serviceImpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bookrental.dto.MemberDto;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.model.Member;
import com.bookrental.repository.MemberRepo;
import com.bookrental.service.MemberService;

@Component
public class MemberImpl implements MemberService {

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean memberOperation(MemberDto memberDto) {

		if (memberDto.getId() != null) {
			Member member = memberRepo.findById(memberDto.getId())
					.orElseThrow(() -> new ResourceNotFoundException("MemberId", String.valueOf(memberDto.getId())));
			if (Boolean.TRUE.equals(memberDto.getToDelete())) {
				memberRepo.delete(member);
				return true;
			}
			BeanUtils.copyProperties(memberDto, member, "id");
			memberRepo.save(member);
			return true;
		} else {
			Member member = new Member();
			BeanUtils.copyProperties(memberDto, member, "id");
			member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
			memberRepo.save(member);
			return true;
		}
	}

	@Override
	public Member getMemberById(Integer memberId) {
		if(memberId<1) {
			throw new ResourceNotFoundException("Please, provide valid member ID.", null);
		}
		return memberRepo.findById(memberId).orElseThrow(()-> new ResourceNotFoundException("Member Id", String.valueOf(memberId)));
	}

	@Override
	public List<Member> getAllMembers() {
		List<Member> members = memberRepo.findAll();
		return members;
	}

}
