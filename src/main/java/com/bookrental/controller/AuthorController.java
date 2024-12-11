package com.bookrental.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.AuthorDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.AuthorService;

@RestController
@RequestMapping(path = "/api/author")
public class AuthorController extends BaseController {

	private final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@PostMapping("/create")
	public ResponseObject createAuthor(@RequestBody AuthorDto authorDto) {

		return getSuccessResponse("Success !!", authorService.authorOperation(authorDto));
	}

	@GetMapping("/{authorId}")
	public ResponseObject getAuthorById(@PathVariable Integer authorId) {

		return getSuccessResponse("Success !!", authorService.getAuthorById(authorId));
	}

	@GetMapping("/")
	public ResponseObject getAllAuthors() {

		return getSuccessResponse("Success !!", authorService.getAllAuthors());
	}

}
