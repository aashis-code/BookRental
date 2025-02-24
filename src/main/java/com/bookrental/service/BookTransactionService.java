package com.bookrental.service;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.mapper.booktransaction.DashBoardBookTransaction;
import com.bookrental.mapper.booktransaction.DashBoardMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface BookTransactionService {

    boolean bookRentCreateAndUpdate(BookTransactionDto bookTransactionDto);

    PaginatedResponse getPaginatedBookTransaction(BookPaginationRequest paginationRequest);

    void getBookTransactionOnExcel(BookPaginationRequest bookPaginationRequest, HttpServletResponse response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException ;

    void getBookTransactionLessThanOneDayRemain();

    DashBoardBookTransaction getDashBoardMapper();

}
