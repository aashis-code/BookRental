package com.bookrental.controller;

import com.bookrental.dto.RoleDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController extends BaseController {


    private final RoleService roleService;

    @PostMapping("/")
    public ResponseObject roleCUD(@RequestBody @Valid RoleDto roleDto) {
        boolean result = roleService.roleSaveAndUpdate(roleDto);
        return getSuccessResponse("Successfully updated role !!", result);
    }

    @GetMapping("/{roleId}")
    public ResponseObject getRoleById(@PathVariable Integer roleId) {
        return getSuccessResponse("Success !!", roleService.getRoleById(roleId));
    }

    @GetMapping("/")
    public ResponseObject getAllRoles() {
        return getSuccessResponse("Success !!", roleService.getAllRoles());
    }
    
	@GetMapping("/paginated")
	public ResponseObject getPaginatedRoles(@RequestBody PaginationRequest paginationRequest) {
		return getSuccessResponse("Successfully fetched paginated data.", roleService.getPaginatedRoleList(paginationRequest.getPage(), paginationRequest.getSize()));
	}

    @DeleteMapping("/{memberId}")
    public ResponseObject deleteMember(@PathVariable Integer memberId) {
    	roleService.roleDelete(memberId);
        return getSuccessResponse("Successfully deleted member !!", true);
    }

}
