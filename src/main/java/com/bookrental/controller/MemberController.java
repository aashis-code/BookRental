package com.bookrental.controller;

import com.bookrental.dto.MemberDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/member")
public class MemberController extends BaseController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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

    @DeleteMapping("/{memberId}")
    public ResponseObject deleteMember(@PathVariable Integer memberId) {
        return getSuccessResponse("Successfully deleted member !!", memberService.deleteMember(memberId));
    }

}
