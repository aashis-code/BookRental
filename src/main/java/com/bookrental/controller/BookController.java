package com.bookrental.controller;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookrental.dto.BookAddRequest;
import com.bookrental.dto.FilterRequest;
import com.bookrental.dto.ListOfBookRequest;
import com.bookrental.helper.ExcelHelper;
import com.bookrental.helper.ResponseObject;
import com.bookrental.model.Book;
import com.bookrental.service.BookService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/book")
public class BookController extends BaseController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}


	@PostMapping("/")
	public ResponseObject uploadBooksFromJson(@ModelAttribute @Valid ListOfBookRequest bookAddRequests) {

		return getSuccessResponse("Books successfully added !", bookService.bookSaveAndUpdate(bookAddRequests));
	}

	@GetMapping("/")
	public ResponseObject getAllBooks() {
		return getSuccessResponse("Successfully fetched all books !!", bookService.getAllBooks());
	}

	@GetMapping("/paginated")
	public ResponseObject getPagenatedBooks(@RequestBody FilterRequest filterRequest) {
		return getSuccessResponse("Successfully fetched paginated data.",
				bookService.getPaginatedBookList(filterRequest));
	}

	@GetMapping("/{bookId}")
	public ResponseObject getBookById(@PathVariable Integer bookId) {
		return getSuccessResponse("Successfully fetch book by bookId !!", bookService.getBookById(bookId));
	}

	@DeleteMapping("/{bookId}")
	public ResponseObject deleteBookById(@PathVariable Integer bookId) {
		bookService.deleteBook(bookId);
		return getSuccessResponse("Success !!", true);
	}

	@GetMapping("/excel")
	public void exportExcel(HttpServletResponse response) {
		bookService.getBookOnExcel(response);
		}
	
	@GetMapping("/category/{categoryId}")
	public ResponseObject getAllBooksByCategory(@PathVariable Integer categoryId) {
		return getSuccessResponse("Books are successfully fetched !!", bookService.getBooksByCategory(categoryId));
	}
	
	@GetMapping("/author/{authorId}")
	public ResponseObject getAllBooksByAuthor(@PathVariable Integer authorId) {
		return getSuccessResponse("Books are successfully fetched !!", bookService.getBooksByAuhtor(authorId));
	}

}
