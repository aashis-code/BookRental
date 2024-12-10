package com.bookrental.service;


import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.repository.MemberRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepo memberRepo;

    public MemberDetailsService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        return memberRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Email", username));
    }
}

