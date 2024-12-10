package com.bookrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.helper.ResponseObject;
import com.bookrental.service.BookTransactionService;

@RestController
@RequestMapping(path = "/api/book-transaction")
public class BookTransactionController {

	@Autowired
	private BookTransactionService bookTransactionService;

	@PostMapping("/member-id/{memberId}/book-id/{bookId}")
	public ResponseObject rentBookByMember(@PathVariable Integer memberId, @PathVariable Integer bookId, @RequestParam(value="returnBook", defaultValue = "false") Boolean returnBook) {

		boolean bookRent = bookTransactionService.bookRentOperation(memberId, bookId, returnBook);

		if (bookRent == false) {
			return new ResponseObject(false, "Internal Server error occur !!", null);
		}

		return new ResponseObject(true, "Successffully rented the book", null);
	}
}
