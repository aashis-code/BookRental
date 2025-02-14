package com.bookrental.controller;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.constants.MessageConstants;
import com.bookrental.helper.constants.ModuleNameConstants;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.service.BookTransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/book-transaction")
@Tag(name = "BookTransaction", description = "Endpoints for managing Book renting and return related activities.")
public class BookTransactionController extends BaseController {

    private final BookTransactionService bookTransactionService;

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public ResponseObject rentBookByMember(@RequestBody @Valid BookTransactionDto bookTransactionDto) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_UPDATE, ModuleNameConstants.BOOK_TRANSACTION_CONTROLLER), bookTransactionService.bookRentCreateAndUpdate(bookTransactionDto));
    }

    @PostMapping("/paginated")
    public ResponseObject getPaginatedBookTransaction(@RequestBody BookPaginationRequest paginationRequest) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_TRANSACTION_CONTROLLER), bookTransactionService.getPaginatedBookTransaction(paginationRequest));
    }
}
