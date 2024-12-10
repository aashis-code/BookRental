package com.bookrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookrental.helper.RentType;
import com.bookrental.model.BookTransaction;
import com.bookrental.model.Member;

@Repository
public interface BookTransactionRepo extends JpaRepository<BookTransaction, Integer> {


	List<BookTransaction> findByMemberAndRentStatus(Member member, RentType rentType);

}
