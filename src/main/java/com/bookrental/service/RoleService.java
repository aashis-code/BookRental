package com.bookrental.service;

import com.bookrental.model.Role;
import org.springframework.stereotype.Service;

import com.bookrental.dto.RoleDto;

import java.util.List;

public interface RoleService {

	boolean roleSaveAndUpdate(RoleDto roleDto);

	Role getRoleById(int roleId);

	List<Role> getAllRoles();

	boolean roleDelete(int roleId);

}
