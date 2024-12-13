package com.bookrental.serviceimpl;

import com.bookrental.dto.MemberDto;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.model.Member;
import com.bookrental.repository.MemberRepo;
import com.bookrental.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberImpl implements MemberService {

    private final MemberRepo memberRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean memberOperation(MemberDto memberDto) {
        Member member;
        if (memberDto.getId() != null) {
            member = memberRepo.findById(memberDto.getId()).orElseThrow(() -> new ResourceNotFoundException("MemberId", String.valueOf(memberDto.getId())));
            CoustomBeanUtils.copyNonNullProperties(memberDto, member);
            memberRepo.save(member);
        } else {
            member = new Member();
            BeanUtils.copyProperties(memberDto, member, "id");
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
        Member member = memberRepo.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Member Id", String.valueOf(memberId)));
        CoustomBeanUtils.copyNonNullProperties(member,memberDto);
        return memberDto;
    }

    @Override
    public List<MemberDto> getAllMembers() {
        return memberRepo.findAll().stream().map(member -> {
            MemberDto memberDto = new MemberDto();
            CoustomBeanUtils.copyNonNullProperties(member,memberDto);
            return memberDto;
        }).toList();
    }

    @Override
    public boolean deleteMember(Integer memberId) {
        if (memberId < 1) {
            throw new ResourceNotFoundException("Please, provide valid member ID.", null);
        }
        Member member = this.memberRepo.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Member Id", String.valueOf(memberId)));
        this.memberRepo.delete(member);
        return true;
    }
}
