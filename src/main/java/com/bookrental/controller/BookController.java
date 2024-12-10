package com.bookrental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookrental.dto.BookAddRequest;
import com.bookrental.helper.ExcelHelper;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.BookService;

import lombok.Getter;

@RestController
@RequestMapping(path = "/api/book")
public class BookController {

	@Autowired
	private BookService bookService;

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
	public ResponseObject uploadBooksFromJson(@RequestBody List<BookAddRequest> bookAddRequests) {
		bookService.addBooks(bookAddRequests);
		return new ResponseObject(true, "Books successfully added !", null);
	}
	
	@GetMapping("/")
	public ResponseObject getAllBooks() {
		return new ResponseObject(true,"Fetched successfully",bookService.getAllBooks());
	}

}
