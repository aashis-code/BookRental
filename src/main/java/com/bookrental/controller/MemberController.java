package com.bookrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.MemberDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.MemberService;

@RestController
@RequestMapping(path = "/api/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/")
	public ResponseObject addMember(@RequestBody MemberDto memberDto) {
		boolean result = memberService.memberOperation(memberDto);
		return new ResponseObject(true, "Success on member entity operation !!", result);
	}

}
