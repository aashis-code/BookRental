package com.bookrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.RoleDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/")
	public ResponseObject roleCUD(@RequestBody @Valid RoleDto roleDto) {
		boolean result = roleService.roleOperation(roleDto);
		return getSuccessResponse("Success !!", result);
	}

}
