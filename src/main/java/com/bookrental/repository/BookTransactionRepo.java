package com.bookrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrental.model.BookTransaction;

@Repository
public interface BookTransactionRepo extends JpaRepository<BookTransaction, Integer> {

}
