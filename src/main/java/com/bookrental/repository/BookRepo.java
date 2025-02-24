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

import com.bookrental.model.Author;
import com.bookrental.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

	@Modifying
	@Query(value = "update book set stock_count = stock_count -1 where id= ?1 and stock_count > 0", nativeQuery = true)
	int decrementBookStock(Integer bookId);

	@Modifying
	@Query(value = "update book set stock_count = stock_count + 1 where id= ?1 and stock_count >= 0", nativeQuery = true)
	int incrementBookStock(Integer bookId);

	@Modifying
	@Query(value = "update book set stock_count = stock_count + ?2 where isbn= ?1 and stock_count >= 0", nativeQuery = true)
	int incrementBookStockByIsbn(String isbn, Integer addStock);

	@Query(value = "select photo as path from book where id = ?1", nativeQuery = true)
	Optional<String> getBookImagePath(Integer id);

	@Transactional
	@Modifying
	@Query(value = "update book set deleted=true where id= ?1", nativeQuery = true)
	int deleteBookById(Integer bookId);

	@Modifying
	@Query(value = "update book set deleted=false where id= ?1", nativeQuery = true)
	boolean revertDelete(Integer bookId);

	@Query(value = "select * from book where id= ?1 and stock_count>0 and deleted=false", nativeQuery = true)
	Optional<Book> isBookAvailbleToRent(Integer bookId);

	Optional<Book> findByIsbn(String isbn);
	
	Optional<Book> findByIdAndDeleted(Integer bookId, Boolean deleted);
	
	List<Book> findAllByDeleted(Boolean deleted);
	
    @Query(value = "select id, name, isbn,number_of_pages as \"numberOfPages\",published_date as \"publishedDate\", rating, photo, stock_count as \"stockCount\", to_char(created_date, 'YYYY-MM-DD HH:MI:SS') as \"createdDate\",\n" +
			"to_char(modified_date, 'YYYY-MM-DD HH:MI:SS') as \"lastModifiedDate\" from book where name ilike concat('%',?1,'%')\n" +
			"and created_date between coalesce(?2, created_date) and coalesce(?3, created_date) and (?4 is null or deleted = ?4)\n" +
			"order by ?5 ", nativeQuery = true)
	Page<Map<String, Object>> filterBookAndPagination(
			                   String keyword,
			                   LocalDate startDate,
			                   LocalDate endDate,
	                           Boolean deleted,
							   String orderBy,
	                           Pageable pageable);

	
	@Query(value="select * from book where category_id = ?1", nativeQuery = true)
	List<Book> findBooksByCategoryId(Integer categoryId);
	
	List<Book> findByAuthors(Author author);
}
