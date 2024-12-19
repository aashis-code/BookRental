package com.bookrental.service;

import java.util.List;

import com.bookrental.dto.RoleDto;
import com.bookrental.model.Role;

public interface RoleService {

	boolean roleSaveAndUpdate(RoleDto roleDto);

	Role getRoleById(int roleId);

	List<Role> getAllRoles();

	List<Role> getPaginatedRoleList(Integer pageNo, Integer pageSize);

	void roleDelete(int roleId);

}
