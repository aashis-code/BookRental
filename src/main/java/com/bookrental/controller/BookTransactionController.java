package com.bookrental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.BookTransactionService;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/book-transaction")
public class BookTransactionController extends BaseController {

	private final BookTransactionService bookTransactionService;

	@PostMapping("/")
	public ResponseObject rentBookByMember(@RequestBody @Valid BookTransactionDto bookTransactionDto) {
		return getSuccessResponse("Success !!", bookTransactionService.bookRentCreateAndUpdate(bookTransactionDto));
	}
}
