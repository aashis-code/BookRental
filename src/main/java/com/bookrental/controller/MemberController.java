package com.bookrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.MemberDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/member")
public class MemberController extends BaseController{
	
	private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService=memberService;
	}
	
	@PostMapping("/")
	public ResponseObject addMember(@RequestBody @Valid MemberDto memberDto) {
		boolean result = memberService.memberOperation(memberDto);
		return new ResponseObject(true, "Success on member entity operation !!", result);
	}
	
	@GetMapping("/{memberId}")
	public ResponseObject getMemberById(@PathVariable Integer memberId) {
		
		return getSuccessResponse("Success !!", memberService.getMemberById(memberId));
	}
	
	@GetMapping("/")
	public ResponseObject getAllMembers() {
		
		return getSuccessResponse("Success !!", memberService.getAllMembers());
	}

}
