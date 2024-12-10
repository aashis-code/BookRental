package com.bookrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.AuthorDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.AuthorService;

@RestController
@RequestMapping(path = "/api/author")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@PostMapping(value = "/create")
	public ResponseObject createAuthor(@RequestBody AuthorDto authorDto) {
		boolean result = authorService.authorOperation(authorDto);
		return new ResponseObject(true, "Operation success !!", result);
	}
}
