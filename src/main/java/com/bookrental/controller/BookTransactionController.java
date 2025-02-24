package com.bookrental.controller;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.constants.MessageConstants;
import com.bookrental.helper.constants.ModuleNameConstants;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.mapper.booktransaction.DashBoardBookTransaction;
import com.bookrental.service.BookTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/book-transaction")
@Tag(name = "BookTransaction", description = "Endpoints for managing Book renting and return related activities.")
public class BookTransactionController extends BaseController {

    private final BookTransactionService bookTransactionService;

    @Operation(
            summary = "This api is used to rent book.",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseObject.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public ResponseObject rentBookByMember(@RequestBody @Valid BookTransactionDto bookTransactionDto) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_UPDATE, ModuleNameConstants.BOOK_TRANSACTION_CONTROLLER), bookTransactionService.bookRentCreateAndUpdate(bookTransactionDto));
    }

    @Operation(
            summary = "This api is used to fetch pageable book response.",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseObject.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @PostMapping("/paginated")
    public ResponseObject getPaginatedBookTransaction(@RequestBody BookPaginationRequest paginationRequest) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.BOOK_TRANSACTION_CONTROLLER), bookTransactionService.getPaginatedBookTransaction(paginationRequest));
    }

    @Operation(
            summary = "This api is used to fetch pageable book response in excel sheet.",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseObject.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @PostMapping("/excel")
    public void getPaginatedBookTransactionInExcel(@RequestBody BookPaginationRequest paginationRequest, HttpServletResponse response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        bookTransactionService.getBookTransactionOnExcel(paginationRequest,response);
 }

    @Operation(
            summary = "This api is used to fetch dashboard for librarian or admin.",
            description = "This helps to get insights about historical book rental activities.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Operation.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DashBoardBookTransaction.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @GetMapping("/dash-board")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public ResponseObject dashBoard() {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_UPDATE, ModuleNameConstants.BOOK_TRANSACTION_CONTROLLER), bookTransactionService.getDashBoardMapper());
    }
}
