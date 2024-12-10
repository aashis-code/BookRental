package com.bookrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Book;

import jakarta.transaction.Transactional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{
	
	@Modifying
	@Transactional
	@Query("update Book b set b.stockCount = b.stockCount -1 where b.id= :bookId and b.stockCount > 0")
	int bookStockDecrement(@Param("bookId") Integer bookId);
	
	@Modifying
	@Transactional
	@Query("update Book b set b.stockCount = b.stockCount +1 where b.id= :bookId and b.stockCount >= 0")
	int bookStockIncrement(@Param("bookId") Integer bookId);

}
