package com.bookrental.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookrental.dto.RoleDto;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.model.Role;
import com.bookrental.repository.RoleRepo;
import com.bookrental.service.RoleService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Component
public class RoleImpl implements RoleService{
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public boolean roleOperation(RoleDto roleDto) {
		
		if(roleDto.getId() != null) {
			Role role = roleRepo.findById(roleDto.getId()).orElseThrow(()-> new ResourceNotFoundException("RoleId", String.valueOf(roleDto.getId())));
			if(Boolean.TRUE.equals(roleDto.getToDelete())) {
				roleRepo.delete(role);
				return true;
			}
			BeanUtils.copyProperties(roleDto, role);
			role.setName(role.getName().toUpperCase());
			roleRepo.save(role);
		} else {
			Role role = new Role();
			BeanUtils.copyProperties(roleDto, role,"id");
			role.setName(role.getName().toUpperCase());
			roleRepo.save(role);
		}
		return true;
	}

}
