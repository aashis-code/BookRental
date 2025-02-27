package com.bookrental.controller;

import com.bookrental.dto.AssignRoleDto;
import com.bookrental.dto.MemberDto;
import com.bookrental.dto.PasswordResetRequestDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.service.MemberService;
import com.bookrental.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/member")
@Tag(name = "Member", description = "Endpoints for managing Member related activities.")
public class MemberController extends BaseController {

    private final MemberService memberService;
    private final PasswordResetService passwordResetService;

    public MemberController(MemberService memberService, PasswordResetService passwordResetService) {
        this.memberService = memberService;
        this.passwordResetService = passwordResetService;
    }

    @Operation(
            summary = "Save and Update Member.",
            description = "This method helps to save and update Member related information.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MemberDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @PostMapping("")
    @PreAuthorize("hasPermission(#memberDto,'remove_librarian')")
    public ResponseObject addMember(@RequestBody @Valid MemberDto memberDto) {

        return new ResponseObject(true, "Success on member entity operation !!", memberService.saveAndUpdateMember(memberDto));
    }

    @GetMapping("/{memberId}")
    @PreAuthorize("hasPermission(#memberId,'MEMBER','fetch_profile')")
    public ResponseObject getMemberById(@PathVariable Integer memberId) {
        MemberDto member = memberService.getMemberById(memberId);
        return getSuccessResponse("Success !!", member);
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/assign-roles")
    public ResponseObject assignRolesToMember(@RequestBody AssignRoleDto assignRoleDto) {
        return getSuccessResponse("Successfully assigned roles !!", memberService.assignRoles(assignRoleDto.getMemberId(), assignRoleDto.getRoles()));
    }

    @PostMapping("/send-token")
    public ResponseObject sendPasswordResetToken(@RequestBody PasswordResetRequestDto passwordResetRequestDto) {
        return getSuccessResponse("Successfully send reset token.", this.passwordResetService.generateAndSendToken(passwordResetRequestDto));
    }

    @PostMapping("/reset-password")
    public ResponseObject resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequestDto) {
        return getSuccessResponse("Successfully updated password.", this.passwordResetService.validateAndResetPassword(passwordResetRequestDto));
    }

}
