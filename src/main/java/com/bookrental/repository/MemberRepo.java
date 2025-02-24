package com.bookrental.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Book;
import com.bookrental.model.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {

	Optional<Member> findByEmail(String email);

	Optional<Member> findByEmailAndDeleted(String email, boolean deleted);

	List<Member> findByDeleted(Pageable page, Boolean deleted);

	Optional<Member> findByIdAndDeleted(Integer memberId, Boolean deleted);

	@Transactional
	@Query(value = "selectfilterMemberPaginated * from member where email = ?1 or mobile_number = ?2 and deleted = ?3", nativeQuery = true)
	List<Member> findByEmailOrPhoneNumber(String email, String phoneNumber, Boolean deleted);

	@Transactional
	@Modifying
	@Query(value = "update member set deleted=true where id= ?1", nativeQuery = true)
	int deleteMemberById(Integer memberId);

	@Modifying
	@Query(value = "update member set deleted=false where id= ?1", nativeQuery = true)
	boolean revertMemberDeleteById(Integer memberId);
	
	@Query(value="select * from member where deleted = ?1", nativeQuery = true)
	List<Member> findAllMember(Boolean deleted);
	
	@Query(value = "select id, name, email, mobile_number as \"mobileNumber\", address , to_char(created_date, 'YYYY-MM-DD HH24:MI:SS') as \"createdDate\", "
			+ "to_char(modified_date, 'YYYY-MM-DD HH24:MI:SS') as \"lastModifiedDate\" from member where "
			+ "(?1 is null or name ilike concat('%',?1,'%')) "
			+ "and created_date between coalesce(?2, created_date) and coalesce(?3, created_date) and (?4 is null or deleted = ?4)", 
			nativeQuery = true)
	Page<Map<String, Object>> filterMemberPaginated(
			                   String keyword,
			                   LocalDate startDate,
			                   LocalDate endDate,
	                           Boolean deleted,
	                           Pageable pageable);

}
