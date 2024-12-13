package com.bookrental.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.BookAddRequest;
import com.bookrental.dto.ListOfBookRequest;
import com.bookrental.model.Book;

@Service
public interface BookService {

	boolean bookSaveAndUpdate(ListOfBookRequest bookAddRequests);

	List<BookAddRequest> getAllBooks();

	Book getBookById(Integer bookId);
	
	boolean deleteBook(Integer bookId);

	ByteArrayInputStream getBookOnExcel();
}
