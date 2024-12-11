package com.bookrental.service;

import org.springframework.stereotype.Service;

import com.bookrental.dto.BookTransactionDto;

@Service
public interface BookTransactionService {
	
	boolean bookRentCUD(BookTransactionDto bookTransactionDto);
	

}
