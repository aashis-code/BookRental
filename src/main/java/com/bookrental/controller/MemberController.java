package com.bookrental.controller;

import com.bookrental.dto.AssignRoleDto;
import com.bookrental.dto.MemberDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.pagination.PaginationRequest;
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

    @PostMapping("")
    public ResponseObject addMember(@RequestBody @Valid MemberDto memberDto) {

        return new ResponseObject(true, "Success on member entity operation !!", memberService.saveAndUpdateMember(memberDto));
    }

    @GetMapping("/{memberId}")
    public ResponseObject getMemberById(@PathVariable Integer memberId) {

        return getSuccessResponse("Success !!", memberService.getMemberById(memberId));
    }

    @GetMapping("")
    public ResponseObject getAllMembers() {

        return getSuccessResponse("Success !!", memberService.getAllMembers());
    }

    @GetMapping("/paginated")
    public ResponseObject getPaginatedMemberList(@RequestBody PaginationRequest filterRequest) {
        return getSuccessResponse("Successfully fetched paginated data.", memberService.getPaginatedMemberList(filterRequest));
    }

    @DeleteMapping("/{memberId}")
    public ResponseObject deleteMember(@PathVariable Integer memberId) {
        memberService.deleteMember(memberId);
        return getSuccessResponse("Successfully deleted member !!", true);
    }

    @PostMapping("/assign-roles")
    public ResponseObject assignRolesToMember(@RequestBody AssignRoleDto assignRoleDto) {
        return getSuccessResponse("Successfully assigned roles !!", memberService.assignRoles(assignRoleDto.getMemberId(), assignRoleDto.getRoles()));
    }

}
