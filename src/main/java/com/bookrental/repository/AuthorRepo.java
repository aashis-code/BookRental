package com.bookrental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Author;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {
	
	Optional<Author>  findByEmail(String email);

}
