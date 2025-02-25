package com.bookrental.service;

import com.bookrental.dto.BookDto;
import com.bookrental.dto.BookResponse;
import com.bookrental.dto.ListOfBookRequest;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.model.Book;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BookService {

	boolean bookSaveAndUpdate(ListOfBookRequest bookAddRequests);

	List<BookResponse> getAllBooks();

	List<Map<String,Object>> getAllBooksMapper();

	PaginatedResponse getPaginatedBookList(BookPaginationRequest paginationRequest);

	Map<String, Object> getBookById(Integer bookId);

	void deleteBook(Integer bookId);

	void getBookOnExcel(HttpServletResponse response);
	
	List<BookDto> getBooksByCategory(Integer categoryId);
	
	List<BookDto> getBooksByAuthor(Integer authorId);

	void getImageByBookId(HttpServletResponse response, Integer bookId);
}
