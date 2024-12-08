package com.bookrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.AuthorDto;
import com.bookrental.service.AuthorService;

@RestController
@RequestMapping(path = "/api/author")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	
	@PostMapping(value =  "/create")
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto){
		AuthorDto savedAuthorDto = authorService.addAuthor(authorDto);
		return new ResponseEntity<AuthorDto>(savedAuthorDto, HttpStatus.CREATED);
	}
}
