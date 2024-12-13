package com.bookrental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Author;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {

	Optional<Author> findByEmailAndDeleted(String email, boolean deleted);

	List<Author> findByDeleted(Boolean deleted);

	Optional<Author> findByIdAndDeleted(Integer authorId, Boolean deleted);

	@Query(value = "select * from author where email = ?1 or mobile_number = ?2 and deleted = ?3", nativeQuery = true)
	List<Author> findByEmailOrPhoneNumber(String email, String phoneNumber, Boolean deleted);

	@Modifying
	@Query(value = "update author set deleted=true where id= ?1", nativeQuery = true)
	boolean deleteAuthorById(Integer authorId);

	@Modifying
	@Query(value = "update author set deleted=false where id= ?1", nativeQuery = true)
	boolean revertAuthorDeleteById(Integer authorId);
}
