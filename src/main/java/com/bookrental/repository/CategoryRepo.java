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

import com.bookrental.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	Optional<Category> findByName(String name);

	@Transactional
	@Modifying
	@Query(value = "update category set deleted = true where id = ?1", nativeQuery = true)
	int deleteCategoryById(Integer categoryId);

	Optional<Category> findByIdAndDeleted(Integer id, Boolean deleted);

	@Query(value = "select * from category where deleted = ?1", nativeQuery = true)
	List<Category> findAllCategoryByIsDeleted(Boolean deleted);

	List<Category> findAllByDeleted(Pageable page, Boolean deleted);

	@Query(value = "select id, name, description , created_by as \"createdBy\" , to_char(created_date, 'YYYY-MM-DD HH:MI:SS') as \"createdDate\",\n" +
			"last_modified_by as \"lastModifiedBy\", to_char(modified_date, 'YYYY-MM-DD HH:MI:SS') as \"lastModifiedDate\" from category where \n" +
			"(?1 is null or name ilike concat('%',?1,'%') or description ilike concat('%',?1,'%'))\n" +
			"and created_date between coalesce(?2, created_date) and coalesce(?3, created_date) and (?4 is null or deleted = ?4)",
			nativeQuery = true)
	Page<Map<String, Object>> filterCategoryPaginated(String keyword, LocalDate startDate, LocalDate endDate, Boolean deleted,
			Pageable pageable);

}
