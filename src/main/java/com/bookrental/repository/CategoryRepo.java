package com.bookrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

	
	
}
