package com.bookrental.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bookrental.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	Optional<Category> findByName(String name);

	@Modifying
	@Query(value = "update category set deleted = true where id = ?1", nativeQuery = true)
	int deleteCategoryById(Integer categoryId);

	Optional<Category> findByIdAndDeleted(Integer id, Boolean deleted);

	@Query(value = "select * from category where deleted = ?1", nativeQuery = true)
	List<Category> findAllCategoryByIsDeleted(Boolean deleted);

	List<Category> findAllByDeleted(Pageable page, Boolean deleted);

	@Query(value = "SELECT * FROM category WHERE name ILIKE %?1% AND created_date BETWEEN ?2 AND ?3 AND deleted = ?4", nativeQuery = true)
	Page<Category> findByDeleted(String keyword, LocalDate startDate, LocalDate endDate, Boolean deleted,
			Pageable pageable);

}
