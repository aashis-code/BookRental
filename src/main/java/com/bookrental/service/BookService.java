package com.bookrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.BookAddRequest;
import com.bookrental.model.Book;

@Service
public interface BookService {

	boolean bookCUDOperation(List<BookAddRequest> bookAddRequests);

	List<BookAddRequest> getAllBooks();

	Book getBookById(Integer bookId);
}
