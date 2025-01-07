package com.bookrental.controller;

import com.bookrental.dto.AuthorDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.constants.MessageConstants;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.service.AuthorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/author")
@SecurityRequirement(name = "bookRental")
public class AuthorController extends BaseController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("")
    public ResponseObject createAndUpdateAuthor(@RequestBody @Valid AuthorDto authorDto) {

        return getSuccessResponse(MessageConstants.CRUD_UPDATE, authorService.saveAndUpdateAuthor(authorDto));
    }

    @GetMapping("/{authorId}")
    public ResponseObject getAuthorById(@PathVariable Integer authorId) {
        return getSuccessResponse(MessageConstants.CRUD_GET, authorService.getAuthorById(authorId));
    }

    @GetMapping("/")
    public ResponseObject getAllAuthors() {
        return getSuccessResponse(MessageConstants.CRUD_GET, authorService.getAllAuthors());
    }

    @GetMapping("/paginated")
    public ResponseObject getPaginatedAuthors(@RequestBody PaginationRequest filterRequest) {
        return getSuccessResponse(MessageConstants.CRUD_GET, authorService.getPaginatedAuthorList(filterRequest));
    }

    @DeleteMapping("/{deleteId}")
    public ResponseObject deleteAuthor(@PathVariable Integer deleteId) {
        authorService.deleteAuthor(deleteId);
        return getSuccessResponse(MessageConstants.CRUD_DELETE, true);
    }

}
