package com.bookrental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {

	Optional<Member> findByEmail(String email);

	Optional<Member> findByEmailAndDeleted(String email, boolean deleted);

	List<Member> findByDeleted(Boolean deleted);

	Optional<Member> findByIdAndDeleted(Integer memberId, Boolean deleted);

	@Query(value = "select * from member where email = ?1 or mobile_number = ?2 and deleted = ?3", nativeQuery = true)
	List<Member> findByEmailOrPhoneNumber(String email, String phoneNumber, Boolean deleted);

	@Modifying
	@Query(value = "update member set deleted=true where id= ?1", nativeQuery = true)
	boolean deleteMemberById(Integer memberId);

	@Modifying
	@Query(value = "update member set deleted=false where id= ?1", nativeQuery = true)
	boolean revertMemberDeleteById(Integer memberId);

}
