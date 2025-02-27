

package com.bookrental.serviceimpl;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.exceptions.AppException;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.RentType;
import com.bookrental.helper.email.EmailDetails;
import com.bookrental.helper.email.EmailService;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.model.Book;
import com.bookrental.model.BookTransaction;
import com.bookrental.model.Member;
import com.bookrental.repository.BookRepo;
import com.bookrental.repository.BookTransactionRepo;
import com.bookrental.repository.MemberRepo;
import com.bookrental.service.BookTransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookTransactionImpl implements BookTransactionService {

    private final BookRepo bookRepo;

    private final MemberRepo memberRepo;

    private final BookTransactionRepo bookTransactionRepo;

    private final EmailService<BookTransaction> emailService;

    // Renting book Operation
    @Override
    @Transactional
    public boolean bookRentCreateAndUpdate(BookTransactionDto bookTransactionDto) {

        Member member = memberRepo.findById(bookTransactionDto.getMemberId()).orElseThrow(
                () -> new ResourceNotFoundException("MemberId", String.valueOf(bookTransactionDto.getMemberId())));

        Book book;

        BookTransaction rentedBook = bookTransactionRepo.findByMemberAndRentStatus(member, RentType.RENT);

//		Either RETURN the book or update the transaction made by changing the book
        if (rentedBook != null && bookTransactionDto.getId() != null) {

            BookTransaction tranBook = bookTransactionRepo.findById(bookTransactionDto.getId()).orElseThrow(
                    () -> new ResourceNotFoundException("TransactionId", String.valueOf(bookTransactionDto.getId())));

            if (Boolean.TRUE.equals(bookTransactionDto.getToReturn())) {
                rentedBook.setRentStatus(RentType.RETURN);

                bookRepo.incrementBookStock(bookTransactionDto.getBookId());
                bookTransactionRepo.save(rentedBook);
            } else if (bookTransactionDto.getBookId() != null
                    && !bookTransactionDto.getBookId().equals(tranBook.getId())) {

                book = bookRepo.isBookAvailbleToRent(bookTransactionDto.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book is not available to rent.", null));
                rentedBook.setBook(book);
                bookRepo.incrementBookStock(tranBook.getId());
                bookRepo.decrementBookStock(bookTransactionDto.getBookId());
                bookTransactionRepo.save(tranBook);
            }

        } else if (rentedBook != null && bookTransactionDto.getId() == null) {
            throw new AppException("You have already rented book before.");
        } else {
//		RENT new book
            book = bookRepo.isBookAvailbleToRent(bookTransactionDto.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book is not available to rent.", null));

            bookRepo.decrementBookStock(bookTransactionDto.getBookId());

            BookTransaction bookTransaction = BookTransaction.builder().code(UUID.randomUUID().toString())
                    .fromDate(LocalDate.now()).toDate(LocalDate.now().plusDays(bookTransactionDto.getRentDuration()))
                    .rentStatus(RentType.RENT).member(member).book(book).build();
            emailService.sendMailWithAttachment(EmailDetails.builder()
                    .subject("You have rented book.")
                    .recipient("aashisdev057@gmail.com")
                    .attachment("email-template")
                    .build(), bookTransaction);
            bookTransactionRepo.save(bookTransaction);
        }
        return true;
    }


    @Override
    public PaginatedResponse getPaginatedBookTransaction(BookPaginationRequest paginationRequest) {
        Page<Map<String, Object>> response = bookTransactionRepo.filterBookTransactionAndPagination(paginationRequest.getFromDate(), paginationRequest.getToDate(), paginationRequest.getIsDeleted(), paginationRequest.getBookId(), paginationRequest.getMemberId(), paginationRequest.getRentStatus().toString(), paginationRequest.getPageable());
        return PaginatedResponse.builder().content(response.getContent())
                .totalElements(response.getTotalElements()).currentPageIndex(response.getNumber())
                .numberOfElements(response.getNumberOfElements()).totalPages(response.getTotalPages()).build();
    }
}
