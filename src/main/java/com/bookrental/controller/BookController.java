package com.bookrental.controller;

import com.bookrental.dto.ListOfBookRequest;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.constants.MessageConstants;
import com.bookrental.helper.constants.ModuleNameConstants;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/book")
@Tag(name = "Book", description = "Endpoints for managing Book related activities.")
public class BookController extends BaseController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_LIBRARIAN','ROLE_ADMIN')")
    public ResponseObject uploadBooksFromJson(@ModelAttribute @Valid ListOfBookRequest bookAddRequests) {

        return getSuccessResponse("Books successfully added !", bookService.bookSaveAndUpdate(bookAddRequests));
    }

    @GetMapping("")
    public ResponseObject getAllBooks() {
        return getSuccessResponse("Successfully fetched all books !!", bookService.getAllBooks());
    }

    @PostMapping("/paginated")
    public ResponseObject getPaginatedBookList(@RequestBody BookPaginationRequest paginationRequest) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER),
                bookService.getPaginatedBookList(paginationRequest));
    }

    @GetMapping("/by-bookId")
    public ResponseObject getBookById(@RequestBody BookPaginationRequest requestPojo) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER), bookService.getBookById(requestPojo.getBookId()));
    }

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

    @GetMapping("/by-category")
    public ResponseObject getAllBooksByCategory(@RequestBody BookPaginationRequest requestPojo) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER), bookService.getBooksByCategory(requestPojo.getCategoryId()));
    }

    @GetMapping("/by-author")
    public ResponseObject getAllBooksByAuthor(@RequestBody BookPaginationRequest requestPojo) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER), bookService.getBooksByAuthor(requestPojo.getAuthorId()));
    }

    @Operation(summary = "Fetch Image By BookId.")
    @PostMapping("/image")
    public  ResponseObject getAllBookImages(@RequestBody BookPaginationRequest requestPojo, HttpServletResponse response) {
        bookService.getImageByBookId(response, requestPojo.getBookId());
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_CONTROLLER),null);
    }

}
