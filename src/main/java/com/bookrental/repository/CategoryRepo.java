package com.bookrental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bookrental.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	Optional<Category> findByName(String name);
	
	
	@Modifying
	@Query(value = "update category set deleted = true where id = ?1", nativeQuery = true)
	void deleteCategoryById(Integer categoryId);
	

//	@Query(value = "select * from category where id = ?1 and deleted = ?2", nativeQuery = true)
	Optional<Category> findByIdAndDeleted(Integer id, Boolean deleted);
	
	@Query(value ="select * from category where deleted = ?1", nativeQuery = true)
	List<Category> findAllCategoryByIsDeleted(Boolean deleted);
	
//	@Modifying
//	@Query(value ="update category set description = ?1 where id = ?2 and name = ?3", nativeQuery = true)
//	boolean updateDescriptionByIdAndName(String description, Integer id, String name);

}
