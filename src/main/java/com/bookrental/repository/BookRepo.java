package com.bookrental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{
	
	@Modifying
	@Query(value="update book set stock_count = stock_count -1 where id= ?1 and stock_count > 0", nativeQuery = true)
	int decrementBookStock(Integer bookId);
	
	@Modifying
	@Query(value = "update book set stock_count = stock_count + ?1 where id= ?1 and stock_count >= 0", nativeQuery = true)
	int incrementBookStock(Integer bookId, Integer addStock);
	
	@Modifying
	@Query(value = "update book set stock_count = stock_count + ?2 where isbn= ?1 and stock_count >= 0", nativeQuery = true)
	int incrementBookStockByIsbn(String isbn, Integer addStock);
	
	@Modifying
	@Query(value="update book set deleted=true where id= ?1",nativeQuery = true)
	boolean deleteBookById(Integer bookId);
	
	@Modifying
	@Query(value="update book set deleted=false where id= ?1",nativeQuery = true)
	boolean revertDelete(Integer bookId);
	
	Optional<Book> findByIsbn(String isbn);

}
