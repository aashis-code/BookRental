package com.bookrental.controller;

import com.bookrental.dto.BookDto;
import com.bookrental.dto.ListOfBookRequest;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.constants.MessageConstants;
import com.bookrental.helper.constants.ModuleNameConstants;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.mapper.author.AuthorResponse;
import com.bookrental.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/book")
@Tag(name = "Book", description = "Endpoints for managing Book related activities.")
public class BookController extends BaseController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Save and Update list of books.",
            description = "This method helps to save and update book details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_LIBRARIAN','ROLE_ADMIN')")
    public ResponseObject uploadBooksFromJson(@ModelAttribute @Valid ListOfBookRequest bookAddRequests) {

        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_UPDATE, ModuleNameConstants.BOOK_CONTROLLER), bookService.bookSaveAndUpdate(bookAddRequests));
    }

    @Operation(
            summary = "Get pageable Books by filtering.",
            description = "This method helps to fetch paginated books based on filter.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = List.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @GetMapping("")
    public ResponseObject getAllBooks() {
//        bookService.getAllBooks()
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER), bookService.getAllBooksMapper());
    }

    @Operation(
            summary = "Get pageable Books by filtering.",
            description = "This method helps to fetch paginated books based on filter.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @PostMapping("/paginated")
    public ResponseObject getPaginatedBookList(@RequestBody BookPaginationRequest paginationRequest) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER),
                bookService.getPaginatedBookList(paginationRequest));
    }

    @PostMapping("/by-bookId")
    public ResponseObject getBookById(@RequestBody BookPaginationRequest requestPojo) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER), bookService.getBookById(requestPojo.getBookId()));
    }

    @Operation(
            summary = "Delete Book by Book Id.",
            description = "This method helps to delete book by bookId.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @DeleteMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_LIBRARIAN','ROLE_ADMIN')")
    public ResponseObject deleteBookById(@RequestBody BookPaginationRequest requestPojo) {
        bookService.deleteBook(requestPojo.getBookId());
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_DELETE, ModuleNameConstants.BOOK_CONTROLLER), true);
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) {
        bookService.getBookOnExcel(response);
    }

    @Operation(
            summary = "Get Books by Category.",
            description = "This method helps to fetch books by Category.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @GetMapping("/by-category")
    public ResponseObject getAllBooksByCategory(@RequestBody BookPaginationRequest requestPojo) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER), bookService.getBooksByCategory(requestPojo.getCategoryId()));
    }

    @Operation(
            summary = "Get Books by Author.",
            description = "This method helps to fetch books by Author.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Map.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @GetMapping("/by-author")
    public ResponseObject getAllBooksByAuthor(@RequestBody BookPaginationRequest requestPojo) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER), bookService.getBooksByAuthor(requestPojo.getAuthorId()));
    }

    @Operation(
            summary = "Get Image of book by BookId.",
            description = "This method helps to fetch book image by BookId.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ResponseObject.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @PostMapping("/image")
    public  ResponseObject getAllBookImages(@RequestBody BookPaginationRequest requestPojo, HttpServletResponse response) {
        bookService.getImageByBookId(response, requestPojo.getBookId());
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER),null);
    }

}
