

package com.bookrental.serviceimpl;

import com.bookrental.dto.BookResponse;
import com.bookrental.dto.BookTransactionDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.exceptions.AppException;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.RentType;
import com.bookrental.helper.UserDataConfig;
import com.bookrental.helper.email.EmailDetails;
import com.bookrental.helper.email.EmailService;
import com.bookrental.helper.excel.GenericExcelSheetGenerator;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.helper.pagination.CustomPageable;
import com.bookrental.mapper.booktransaction.BookTransactionDetails;
import com.bookrental.mapper.booktransaction.BookTransactionMapper;
import com.bookrental.mapper.booktransaction.DashBoardBookTransaction;
import com.bookrental.mapper.booktransaction.DashBoardMapper;
import com.bookrental.model.Book;
import com.bookrental.model.BookTransaction;
import com.bookrental.model.Member;
import com.bookrental.repository.BookRepo;
import com.bookrental.repository.BookTransactionRepo;
import com.bookrental.repository.MemberRepo;
import com.bookrental.service.BookTransactionService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookTransactionImpl implements BookTransactionService {

    private final BookRepo bookRepo;

    private final MemberRepo memberRepo;

    private final BookTransactionRepo bookTransactionRepo;

    private final EmailService<BookTransaction> emailService;

    private final UserDataConfig userDataConfig;

    private final DashBoardMapper dashBoardMapper;

    private final BookTransactionMapper bookTransactionMapper;

    private final GenericExcelSheetGenerator<BookTransactionDetails> bookSheetGenerator;

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
                    .recipient(member.getEmail())
                    .attachment("email-template")
                    .build(), bookTransaction);
            bookTransactionRepo.save(bookTransaction);
        }
        return true;
    }


    @Override
    public PaginatedResponse getPaginatedBookTransaction(BookPaginationRequest paginationRequest) {
        Integer memberId;
        if (userDataConfig.getMemberId() == null) {
            throw new AppException("You have not logged in.");
        }
        if (userDataConfig.isAdminOrLibrarian()) {
            memberId = paginationRequest.getMemberId() != null ? paginationRequest.getMemberId() : null;
        } else {
            memberId = userDataConfig.getMemberId();
        }
        Page<Map<String, Object>> response = bookTransactionRepo.filterBookTransactionAndPagination(paginationRequest.getFromDate(), paginationRequest.getToDate(), paginationRequest.getIsDeleted(), paginationRequest.getBookId(), memberId, paginationRequest.getRentStatus() != null ? paginationRequest.getRentStatus().toString() : null, CustomPageable.getPageable(paginationRequest));
        return PaginatedResponse.builder().content(response.getContent())
                .totalElements(response.getTotalElements()).currentPageIndex(response.getNumber())
                .numberOfElements(response.getNumberOfElements()).totalPages(response.getTotalPages()).build();
    }


    @Override
    public void getBookTransactionOnExcel(BookPaginationRequest request, HttpServletResponse response) throws InvocationTargetException{
        Integer offset = (request.getPage() + 1) * request.getSize();
        List<BookTransactionDetails> bookTransactionDetails = bookTransactionMapper.filterBookTransaction(request.getFromDate(),
                request.getToDate(), request.getIsDeleted(), request.getBookId() != null ? request.getBookId() : -1,
                request.getMemberId() != null ? request.getMemberId() : -1,
                request.getRentStatus() != null ? request.getRentStatus().toString() : "", offset, request.getSize());
        ByteArrayInputStream byteArrayInputStream;
        try {
            byteArrayInputStream = bookSheetGenerator.getExcelSheet(bookTransactionDetails);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"bookTransaction.xls\"");
            byteArrayInputStream.transferTo(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException | RuntimeException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DashBoardBookTransaction getDashBoardMapper() {
        return dashBoardMapper.getBookDashBoard();
    }


    @Scheduled(cron = "0 0 12 * * *")
    @Override
    public void getBookTransactionLessThanOneDayRemain() {
        List<Map<String, Object>> bookTran = bookTransactionRepo.findAllBookTransactionForSchedular();

        for (Map<String, Object> bookObj : bookTran) {
            emailService.sendMailWithAttachment(EmailDetails.builder()
                            .subject("Reminder for returning book.")
                            .attachment("book-return-deadline")
                            .msgBody("Please return your book.")
                            .recipient(bookObj.get("email").toString())
//                            .recipient("aashisdev057@gmail.com")
                            .build(),
                    BookTransaction.builder()
                            .book(Book.builder().name(bookObj.get("bookname").toString()).photo(bookObj.get("photo").toString()).build())
                            .member(Member.builder().email(bookObj.get("email").toString()).name(bookObj.get("name").toString()).build())
                            .toDate(LocalDate.parse(bookObj.get("deadline").toString()))
                            .build());
        }
    }
}
