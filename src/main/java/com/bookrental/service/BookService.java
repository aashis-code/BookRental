package com.bookrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.BookAddRequest;

@Service
public interface BookService {

	boolean addBooks(List<BookAddRequest> bookAddRequests);

	List<BookAddRequest> getAllBooks();

}
