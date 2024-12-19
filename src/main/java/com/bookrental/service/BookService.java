package com.bookrental.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.BookDto;
import com.bookrental.dto.BookResponse;
import com.bookrental.dto.FilterRequest;
import com.bookrental.dto.ListOfBookRequest;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.model.Book;

import jakarta.servlet.http.HttpServletResponse;

@Service
public interface BookService {

	boolean bookSaveAndUpdate(ListOfBookRequest bookAddRequests);

	List<BookResponse> getAllBooks();

	PaginatedResponse getPaginatedBookList(FilterRequest filterRequest);

	Book getBookById(Integer bookId);

	void deleteBook(Integer bookId);

	void getBookOnExcel(HttpServletResponse response);
	
	List<BookDto> getBooksByCategory(Integer categoryId);
	
	List<Book> getBooksByAuhtor(Integer authorId);
}
