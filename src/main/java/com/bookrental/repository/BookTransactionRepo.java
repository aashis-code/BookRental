package com.bookrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrental.helper.RentType;
import com.bookrental.model.BookTransaction;
import com.bookrental.model.Member;

@Repository
public interface BookTransactionRepo extends JpaRepository<BookTransaction, Integer> {

	BookTransaction findByMemberAndRentStatus(Member member, RentType rentType);

}
