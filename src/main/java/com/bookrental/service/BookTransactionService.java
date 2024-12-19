package com.bookrental.service;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.dto.FilterRequest;
import com.bookrental.dto.PaginatedResponse;

public interface BookTransactionService {

	boolean bookRentCreateAndUpdate(BookTransactionDto bookTransactionDto);

	PaginatedResponse getPaginatedBookTransaction(FilterRequest filterRequest);
}
