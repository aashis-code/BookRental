package com.bookrental.controller;

import com.bookrental.dto.ListOfBookRequest;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    public ResponseObject uploadBooksFromJson(@ModelAttribute @Valid ListOfBookRequest bookAddRequests) {

        return getSuccessResponse("Books successfully added !", bookService.bookSaveAndUpdate(bookAddRequests));
    }

    @GetMapping("")
    public ResponseObject getAllBooks() {
        return getSuccessResponse("Successfully fetched all books !!", bookService.getAllBooks());
    }

    @GetMapping("/paginated")
    public ResponseObject getPaginatedBookList(@RequestBody BookPaginationRequest paginationRequest) {
        return getSuccessResponse("Successfully fetched paginated data.",
                bookService.getPaginatedBookList(paginationRequest));
    }

    @GetMapping("/by-bookId")
    public ResponseObject getBookById(@RequestBody BookPaginationRequest requestPojo) {
        return getSuccessResponse("Successfully fetch book by bookId !!", bookService.getBookById(requestPojo.getBookId()));
    }

    @DeleteMapping("")
    public ResponseObject deleteBookById(@RequestBody BookPaginationRequest requestPojo) {
        bookService.deleteBook(requestPojo.getBookId());
        return getSuccessResponse("Success !!", true);
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) {
        bookService.getBookOnExcel(response);
    }

    @GetMapping("/by-category")
    public ResponseObject getAllBooksByCategory(@RequestBody BookPaginationRequest requestPojo) {
        return getSuccessResponse("Books are successfully fetched !!", bookService.getBooksByCategory(requestPojo.getCategoryId()));
    }

    @GetMapping("/by-author")
    public ResponseObject getAllBooksByAuthor(@RequestBody BookPaginationRequest requestPojo) {
        return getSuccessResponse("Books are successfully fetched !!", bookService.getBooksByAuthor(requestPojo.getAuthorId()));
    }

    @Operation(summary = "Fetch Image By BookId.")
    @GetMapping("/image")
    public  ResponseObject getAllBookImages(@RequestBody BookPaginationRequest requestPojo, HttpServletResponse response) {
        bookService.getImageByBookId(response, requestPojo.getBookId());
        return getSuccessResponse("Image fetched successfull !!",null);
    }

}
