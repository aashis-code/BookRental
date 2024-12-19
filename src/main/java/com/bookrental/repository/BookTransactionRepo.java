package com.bookrental.repository;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookrental.helper.RentType;
import com.bookrental.model.Book;
import com.bookrental.model.BookTransaction;
import com.bookrental.model.Member;

@Repository
public interface BookTransactionRepo extends JpaRepository<BookTransaction, Integer> {

	BookTransaction findByMemberAndRentStatus(Member member, RentType rentType);

	@Query(value = "select * from book_transaction "
			+ "where created_date between coalesce (?1 , created_date) and "
			+ "coalesce (?2, created_date) and (?3 is null or deleted =?3) and"
			+ "(?4 is null or book_id = ?4) and  (?5 is null or member_id=?5) "
			+ "and (?6 is null or rent_status=?6)", nativeQuery = true)
	Page<BookTransaction> filterBookTransactionAndPagination(
			                   LocalDateTime startDate,
			                   LocalDateTime endDate,
	                           Boolean deleted,
	                           Integer bookId,
	                           Integer memberId,
	                           String rentStatus,
	                           Pageable pageable);
}
