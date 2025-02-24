package com.bookrental.service;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.mapper.booktransaction.DashBoardBookTransaction;
import com.bookrental.mapper.booktransaction.DashBoardMapper;

public interface BookTransactionService {

    boolean bookRentCreateAndUpdate(BookTransactionDto bookTransactionDto);

    PaginatedResponse getPaginatedBookTransaction(BookPaginationRequest paginationRequest);

    void getBookTransactionLessThanOneDayRemain();

    DashBoardBookTransaction getDashBoardMapper();

}
