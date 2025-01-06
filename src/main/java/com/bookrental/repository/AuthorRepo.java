package com.bookrental.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Author;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {

	Optional<Author> findByEmailAndDeleted(String email, boolean deleted);

	List<Author> findByDeleted(Boolean deleted);

	@Query(value = "select * from author where name ilike %?1% and "
			+ "created_date between coalesce(?2, created_date) and "
			+ "coalesce(?3, created_date) and (?4 is null or deleted = ?4)", 
			nativeQuery = true)
	Page<Map<String, Object>> filterAuthorPaginated(String keyword, LocalDate startDate, LocalDate endDate, Boolean deleted,
			Pageable pageable);

	Optional<Author> findByIdAndDeleted(Integer authorId, Boolean deleted);

	boolean existsByEmail(String email);

	@Query(value = "select * from author where email = ?1 or mobile_number = ?2 and deleted = ?3", nativeQuery = true)
	List<Author> findByEmailOrPhoneNumber(String email, String phoneNumber, Boolean deleted);

	@Modifying
	@Query(value = "update author set deleted=true where id= ?1", nativeQuery = true)
	int deleteAuthorById(Integer authorId);

	@Modifying
	@Query(value = "update author set deleted=false where id= ?1", nativeQuery = true)
	boolean revertAuthorDeleteById(Integer authorId);
}
