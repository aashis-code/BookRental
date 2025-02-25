package com.bookrental.controller;

import com.bookrental.dto.RoleDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@Tag(name = "Role", description = "Endpoints for managing Role related activities.")
public class RoleController extends BaseController {


    private final RoleService roleService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseObject roleCUD(@RequestBody @Valid RoleDto roleDto) {
        boolean result = roleService.roleSaveAndUpdate(roleDto);
        return getSuccessResponse("Successfully updated role !!", result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseObject getRoleById(@PathVariable Integer id) {
        return getSuccessResponse("Success !!", roleService.getRoleById(id));
    }

    @GetMapping("")
    public ResponseObject getAllRoles() {
        return getSuccessResponse("Success !!", roleService.getAllRoles());
    }
    
	@PostMapping("/paginated")
	public ResponseObject getPaginatedRoles(@RequestBody PaginationRequest paginationRequest) {
		return getSuccessResponse("Successfully fetched paginated data.", roleService.getPaginatedRoleList(paginationRequest.getPage(), paginationRequest.getSize()));
	}

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseObject deleteRole(@PathVariable Integer id) {
    	roleService.roleDelete(id);
        return getSuccessResponse("Successfully deleted member !!", true);
    }

}
