package com.bookrental.serviceimpl;

import com.bookrental.dto.RoleDto;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.model.Role;
import com.bookrental.repository.RoleRepo;
import com.bookrental.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleImpl implements RoleService {

    private final RoleRepo roleRepo;

    @Override
    public boolean roleSaveAndUpdate(RoleDto roleDto) {
        Role role;
        if (roleDto.getId() != null) {
            role = roleRepo.findById(roleDto.getId()).orElseThrow(() -> new ResourceNotFoundException("RoleId", String.valueOf(roleDto.getId())));
            CoustomBeanUtils.copyNonNullProperties(roleDto, role);
        } else {
            role = new Role();
            BeanUtils.copyProperties(roleDto, role, "id");
        }
        role.setName(role.getName().toUpperCase());
        roleRepo.save(role);
        return true;
    }

    @Override
    public Role getRoleById(int roleId) {
        if (roleId < 1) {
            throw new ResourceNotFoundException("Please, provide valid role ID.", null);
        }
        return roleRepo.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Member Id", String.valueOf(roleId)));

    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public boolean roleDelete(int roleId) {
        if (roleId < 1) {
            throw new ResourceNotFoundException("Please, provide valid role ID.", null);
        }
        Role role = this.roleRepo.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role Id", String.valueOf(roleId)));
        this.roleRepo.delete(role);
        return true;
    }

}



