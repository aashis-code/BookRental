package com.bookrental.serviceimpl;

import com.bookrental.model.Permission;
import com.bookrental.repository.PermissionRepo;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Integer>{

    public PermissionServiceImpl(PermissionRepo permissionRepo) {
        super(permissionRepo);
    }
}
