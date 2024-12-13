package com.bookrental.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.model.Member;
import com.bookrental.repository.MemberRepo;

@Component
public class MemberDetailsService implements UserDetailsService {
	
    private final MemberRepo memberRepo;

    public MemberDetailsService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
         Member member = memberRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Email", username));
         
         return new MemberDetails(member);
    }
}
