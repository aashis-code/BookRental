package com.bookrental.service;

import org.springframework.stereotype.Service;

import com.bookrental.dto.RoleDto;

@Service
public interface RoleService {

	boolean roleOperation(RoleDto roleDto);

}
