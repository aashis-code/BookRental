package com.bookrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {

}