package com.bookrental.service;

import org.springframework.stereotype.Service;

@Service
public interface BookTransactionService {
	
	boolean bookRentOperation(Integer memberId, Integer bookId, Boolean returnBook);
	

}
