package com.bookrental.serviceimpl;

import com.bookrental.dto.RoleDto;
import com.bookrental.exceptions.AppException;
import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.model.Role;
import com.bookrental.repository.RoleRepo;
import com.bookrental.service.RoleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        	Optional<Role> roleFetch = roleRepo.findByName(roleDto.getName().trim().toUpperCase());
        	if(roleFetch.isPresent()) {
        		throw new ResourceAlreadyExist("Role", roleDto.getName());
        	}
            role = new Role();
            CoustomBeanUtils.copyNonNullProperties(roleDto, role);
        }
        role.setName(role.getName().trim().toUpperCase());
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
	public List<Role> getPaginatedRoleList(Integer pageNo, Integer pageSize) {
		Pageable page = PageRequest.of(pageNo, pageSize);
		return roleRepo.findAllByDeleted(page, Boolean.FALSE);
	}

    @Override
    @Transactional
    public void roleDelete(int roleId) {
        if (!roleRepo.existsById(roleId)) {
            throw new AppException("Please, provide valid role ID.");
        }
         int result = this.roleRepo.deleteRoleById(roleId);
         if(result<1) {
        	 throw new ResourceNotFoundException("ROleId", String.valueOf(roleId));
         }
    }

}



