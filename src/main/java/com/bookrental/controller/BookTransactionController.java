package com.bookrental.controller;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.service.BookTransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/book-transaction")
@Tag(name = "BookTransaction", description = "Endpoints for managing Book renting and return related activities.")
public class BookTransactionController extends BaseController {

    private final BookTransactionService bookTransactionService;

    @PostMapping("")
    public ResponseObject rentBookByMember(@RequestBody @Valid BookTransactionDto bookTransactionDto) {
        return getSuccessResponse("Success !!", bookTransactionService.bookRentCreateAndUpdate(bookTransactionDto));
    }

    @GetMapping("/paginated")
    public ResponseObject getPaginatedBookTransaction(@RequestBody BookPaginationRequest paginationRequest) {
        return getSuccessResponse("Successfully fetched paginated data.", bookTransactionService.getPaginatedBookTransaction(paginationRequest));
    }
}
