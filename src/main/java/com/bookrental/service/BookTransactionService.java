package com.bookrental.service;

import com.bookrental.dto.BookTransactionDto;

public interface BookTransactionService {
	
	boolean bookRentCreateAndUpdate(BookTransactionDto bookTransactionDto);
	

}
