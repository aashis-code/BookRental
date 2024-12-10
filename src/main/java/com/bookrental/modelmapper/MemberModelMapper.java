package com.bookrental.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bookrental.dto.MemberDto;
import com.bookrental.model.Member;

public class MemberModelMapper {

	@Autowired
	private ModelMapper modelMapper;

	public Member mamberDtoToMember(MemberDto memberDto) {
		return modelMapper.map(memberDto, Member.class);
	}

	public MemberDto memberToMemberDto(Member member) {
		return modelMapper.map(member, MemberDto.class);
	}

}
