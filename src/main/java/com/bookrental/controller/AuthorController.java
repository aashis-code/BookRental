package com.bookrental.controller;

import com.bookrental.dto.AuthorDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.constants.MessageConstants;
import com.bookrental.helper.constants.ModuleNameConstants;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.service.AuthorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/author")
@SecurityRequirement(name = "bookRental")
@Tag(name = ModuleNameConstants.AUTHOR_CONTROLLER, description = "Endpoints for managing Author related activities.")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
public class AuthorController extends BaseController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("")
    public ResponseObject createAndUpdateAuthor(@RequestBody @Valid AuthorDto authorDto) {

        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_UPDATE, ModuleNameConstants.AUTHOR_CONTROLLER), authorService.saveAndUpdateAuthor(authorDto));
    }

    @GetMapping("/{authorId}")
    public ResponseObject getAuthorById(@PathVariable Integer authorId) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.AUTHOR_CONTROLLER), authorService.getAuthorById(authorId));
    }

    @GetMapping("/")
    public ResponseObject getAllAuthors() {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.AUTHOR_CONTROLLER), authorService.getAllAuthors());
    }

    @PostMapping("/paginated")
    public ResponseObject getPaginatedAuthors(@RequestBody PaginationRequest filterRequest) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.AUTHOR_CONTROLLER), authorService.getPaginatedAuthorList(filterRequest));
    }

    @DeleteMapping("/{deleteId}")
    public ResponseObject deleteAuthor(@PathVariable Integer deleteId) {
        authorService.deleteAuthor(deleteId);
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_DELETE, ModuleNameConstants.AUTHOR_CONTROLLER), true);
    }

}
