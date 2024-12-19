package com.bookrental.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.AuthorDto;
import com.bookrental.dto.FilterRequest;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.AuthorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/author")
@SecurityRequirement(name="bookRental")
public class AuthorController extends BaseController {

	private final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@PostMapping("/create")
	public ResponseObject createAndUpdateAuthor(@RequestBody @Valid AuthorDto authorDto) {

		return getSuccessResponse("Success !!", authorService.saveAndUpdateAuthor(authorDto));
	}

	@GetMapping("/{authorId}")
	public ResponseObject getAuthorById(@PathVariable Integer authorId) {

		return getSuccessResponse("Success !!", authorService.getAuthorById(authorId));
	}

	@GetMapping("/")
	public ResponseObject getAllAuthors() {

		return getSuccessResponse("Success !!", authorService.getAllAuthors());
	}
	
	@GetMapping("/paginated")
	public ResponseObject getPagenatedAuthors(@RequestBody FilterRequest filterRequest) {
		return getSuccessResponse("Successfully fetched paginated data.", authorService.getPaginatedAuthorList(filterRequest));
	}

	@DeleteMapping("/{deleteId}")
	public ResponseObject deleteAuthor(@PathVariable Integer deleteId) {
		     authorService.deleteAuthor(deleteId);
			 return getSuccessResponse("Success !!",true);

	}

}
