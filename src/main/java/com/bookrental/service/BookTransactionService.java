package com.bookrental.service;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.helper.pagination.BookPaginationRequest;

public interface BookTransactionService {

    boolean bookRentCreateAndUpdate(BookTransactionDto bookTransactionDto);

    PaginatedResponse getPaginatedBookTransaction(BookPaginationRequest paginationRequest);


}
