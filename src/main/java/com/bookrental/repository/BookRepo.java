package com.bookrental.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	
	@Query(value = "SELECT * FROM book WHERE name ILIKE %?1% AND published_date BETWEEN ?2 AND ?3 AND deleted = ?4", nativeQuery = true)
	Page<Book> findByDeleted(
			                   String keyword,
			                   LocalDate startDate,
			                   LocalDate endDate,
	                           Boolean deleted,
	                           Pageable pageable);

	
	@Query(value="select * from book where category_id = ?1", nativeQuery = true)
	List<Book> findBooksByCategoryId(Integer categoryId);
	
	List<Book> findByAuthors(Author author);
}
