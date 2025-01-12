package com.bookrental.security;

import com.bookrental.dto.MemberDto;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.model.Member;
import com.bookrental.repository.MemberRepo;
import com.bookrental.repository.RolePermissionRepo;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final RolePermissionRepo rolePermissionRepo;

    private final MemberRepo memberRepo;

    CustomPermissionEvaluator(RolePermissionRepo rolePermissionRepo,MemberRepo memberRepo) {
        this.rolePermissionRepo = rolePermissionRepo;
        this.memberRepo = memberRepo;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

        return hasPrivilege(authentication, targetType, permission.toString().toLowerCase());

    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if ((authentication == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }

        if(!targetId.toString().isEmpty() && targetType.equalsIgnoreCase("Member")) {
           Member member =  this.memberRepo.findById(Integer.valueOf(targetId.toString())).orElseThrow(()-> new ResourceNotFoundException("Member Id",targetId.toString()));
           if(member.getEmail().equals(authentication.getName())) {
               return hasPrivilege(authentication, targetType.toUpperCase(), permission.toString().toLowerCase());
           }
            return false;
        }
        return hasPrivilege(authentication, targetType.toLowerCase(),
                permission.toString().toLowerCase());
    }

    public boolean hasPrivilege(Authentication auth, String targetType, String permission) {

        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            if (grantedAuth.getAuthority().contains("ROLE_ADMIN")) {
                return true;
            }
            List<String> userPermissions = this.rolePermissionRepo.getPermissionsByRole(grantedAuth.toString().substring(5));
            if (userPermissions.contains(permission)) {
                return true;
            }
        }
        return false;
    }
}