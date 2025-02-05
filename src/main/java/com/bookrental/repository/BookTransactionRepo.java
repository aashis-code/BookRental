package com.bookrental.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.bookrental.helper.pagination.BookPaginationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookrental.helper.RentType;
import com.bookrental.model.BookTransaction;
import com.bookrental.model.Member;

@Repository
public interface BookTransactionRepo extends JpaRepository<BookTransaction, Integer> {

	BookTransaction findByMemberAndRentStatus(Member member, RentType rentType);

	@Query(value = "select bt.id,bt.rent_status,bt.created_by ,bt.from_date , bt.to_date ,(bt.to_date::DATE-CURRENT_DATE::DATE) as days_left,\r\n"
			+ "to_char(bt.created_date, 'YYYY-MM-DD HH24:MI:SS') as created_date, bt.last_modified_by, \r\n"
			+ "to_char(bt.modified_date, 'YYYY-MM-DD HH24:MI:SS') as last_modified_date, m.name as member_name, \r\n"
			+ "b.name as book_name from book_transaction bt inner join member m on bt.member_id=m.id inner join book b on bt.book_id=b.id \n"
			+ "where bt.created_date between coalesce (?1 , bt.created_date) and "
			+ "coalesce (?2, bt.created_date) and (?3 is null or bt.deleted =?3) and "
			+ "(?4 is null or bt.book_id = ?4) and  (?5 is null or bt.member_id=?5) "
			+ "and (?6 is null or bt.rent_status=?6) ",	nativeQuery = true)
	Page<Map<String, Object>> filterBookTransactionAndPagination(	
			                   LocalDate startDate,
			                   LocalDate endDate,
	                           Boolean deleted,
	                           Integer bookId,
	                           Integer memberId,
	                           String rentStatus,
	                           Pageable pageable);

	@Query(value ="select bt.id, to_char(bt.to_date,'YYYY-MM-DD') as deadline, b.name as bookname, b.photo, m.email, m.name from book_transaction bt\n" +
			"inner join book b on b.id = bt.book_id \n" +
			"inner join member m on m.id = bt.member_id \n" +
			"where bt.to_date <= (current_date + interval '1 day')  and bt.rent_status = 'RENT'", nativeQuery = true)
	List<Map<String,Object>> findAllBookTransactionForSchedular();
}
