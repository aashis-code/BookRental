package com.bookrental.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookrental.dto.BookAddRequest;
import com.bookrental.dto.ListOfBookRequest;
import com.bookrental.helper.ExcelHelper;
import com.bookrental.helper.ResponseObject;
import com.bookrental.repository.BookRepo;
import com.bookrental.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/book")
public class BookController extends BaseController{

	private final BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService=bookService;
	}

	@PostMapping("/upload-excel")
	public ResponseObject uploadBooksFromExcel(@RequestParam("file") MultipartFile file) {
		try {
			if (!ExcelHelper.isExcelFile(file)) {
				return new ResponseObject(false, "File format does not match with excel type.", null);
			}

			List<BookAddRequest> excelDate = ExcelHelper.readExcelFile(file);

		} catch (Exception e) {
			return new ResponseObject(false, "Internal server error.", null);
		}

		return new ResponseObject(false, "Internal server error.", null);
	}

	@PostMapping("/")
	public ResponseObject uploadBooksFromJson(@RequestBody @Valid ListOfBookRequest bookAddRequests) {
		
		return getSuccessResponse( "Books successfully added !", bookService.bookSaveAndUpdate(bookAddRequests));
	}

	@GetMapping("/")
	public ResponseObject getAllBooks() {
		return getSuccessResponse("Successfully fetched all books !!", bookService.getAllBooks());
	}
	
	@GetMapping("/{bookId}")
	public ResponseObject getBookById(@PathVariable Integer bookId) {
		return getSuccessResponse("Successfully fetch book by bookId !!", bookService.getBookById(bookId));
	}
	
	@DeleteMapping("/{bookId}")
	public ResponseObject deleteBookById(@PathVariable Integer bookId) {
		return getSuccessResponse("Success !!", bookService.deleteBook(bookId));
	}

	@GetMapping("/excel")
	public ResponseEntity<Resource> exportExcel() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=books.xls");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(new InputStreamResource(bookService.getBookOnExcel()));
	}

}
