package com.bookrental.helper;

import com.bookrental.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataConfig {

public Integer getMemberId() {
    MemberDetails memberDetails = (MemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (memberDetails != null) {
        return memberDetails.getUserId();
    }
    return null;
}

public boolean isAdmin() {
   return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
           .stream().map(GrantedAuthority::getAuthority)
           .anyMatch(role -> role.equals("ROLE_ADMIN"));
}

}
