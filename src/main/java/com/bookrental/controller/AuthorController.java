package com.bookrental.controller;

import com.bookrental.dto.AuthorDto;
import com.bookrental.dto.MemberDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.constants.MessageConstants;
import com.bookrental.helper.constants.ModuleNameConstants;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.mapper.author.AuthorResponse;
import com.bookrental.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.catalina.LifecycleState;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(
            summary = "Save or Update Author.",
            description = "This method helps to create or  author by author Id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseObject.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @PostMapping("")
    public ResponseObject createAndUpdateAuthor(@RequestBody @Valid AuthorDto authorDto) {

        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_UPDATE, ModuleNameConstants.AUTHOR_CONTROLLER), authorService.saveAndUpdateAuthor(authorDto));
    }

    @Operation(
            summary = "Get Author by Author Id.",
            description = "This method helps to fetch Author by Author Id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AuthorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @GetMapping("/{id}")
    public ResponseObject getAuthorById(@PathVariable Integer id) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.AUTHOR_CONTROLLER), authorService.getAuthorById(id));
    }

    @Operation(
            summary = "Get List of Authors.",
            description = "This method helps to fetch list of Author.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @GetMapping("/")
    public ResponseObject getAllAuthors() {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.AUTHOR_CONTROLLER), authorService.getAllAuthors());
    }

    @PostMapping("/paginated")
    public ResponseObject getPaginatedAuthors(@RequestBody PaginationRequest filterRequest) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.AUTHOR_CONTROLLER), authorService.getPaginatedAuthorList(filterRequest));
    }

    @Operation(
            summary = "Delete author by author Id.",
            description = "This method helps to delete author by author Id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseObject.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseObject deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_DELETE, ModuleNameConstants.AUTHOR_CONTROLLER), true);
    }

}
