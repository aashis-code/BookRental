package com.bookrental.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.RentType;
import com.bookrental.model.Book;
import com.bookrental.model.BookTransaction;
import com.bookrental.model.Member;
import com.bookrental.repository.BookRepo;
import com.bookrental.repository.BookTransactionRepo;
import com.bookrental.repository.MemberRepo;
import com.bookrental.service.BookTransactionService;

@Component
public class BookTransactionImpl implements BookTransactionService {

	private final BookRepo bookRepo;

	private final MemberRepo memberRepo;

	private final BookTransactionRepo bookTransactionRepo;

	public BookTransactionImpl(BookRepo bookRepo, MemberRepo memberRepo, BookTransactionRepo bookTransactionRepo) {
		this.bookRepo = bookRepo;
		this.memberRepo = memberRepo;
		this.bookTransactionRepo = bookTransactionRepo;
	}

//	Renting book Operation
	@Override
	public boolean bookRentCUD(BookTransactionDto bookTransactionDto) {

		Member member = memberRepo.findById(bookTransactionDto.getMemberId()).orElseThrow(
				() -> new ResourceNotFoundException("Member", String.valueOf(bookTransactionDto.getMemberId())));

		BookTransaction rentedBook = bookTransactionRepo.findByMemberAndRentStatus(member, RentType.RENT);

		Book book = bookRepo.findById(bookTransactionDto.getBookId()).orElseThrow(
				() -> new ResourceNotFoundException("BookId", String.valueOf(bookTransactionDto.getBookId())));

		if (rentedBook != null) {

			if (Boolean.TRUE.equals(bookTransactionDto.getToReturn())) {
				rentedBook.setRentStatus(RentType.RETURN);
				rentedBook.setActiveClosed(false);

				bookRepo.bookStockIncrement(bookTransactionDto.getBookId());
				bookTransactionRepo.save(rentedBook);
				return true;
			} else {
				BookTransaction tranBook = bookTransactionRepo.findById(bookTransactionDto.getId())
						.orElseThrow(() -> new ResourceNotFoundException("TransactionId",
								String.valueOf(bookTransactionDto.getId())));
				rentedBook.setBook(book);
				bookTransactionRepo.save(tranBook);
				return true;
			}
		}

		int value = bookRepo.bookStockDecrement(bookTransactionDto.getBookId());
		if (value < 1) {
			throw new ResourceNotFoundException("Book is out of stock.", null);
		}

		BookTransaction bookTransaction = BookTransaction.builder().code(UUID.randomUUID().toString())
				.fromDate(LocalDate.now()).toDate(LocalDate.now().plusDays(bookTransactionDto.getRentDuration()))
				.rentStatus(RentType.RENT).activeClosed(true).member(member).book(book).build();
		bookTransactionRepo.save(bookTransaction);
		return true;
	}

}
