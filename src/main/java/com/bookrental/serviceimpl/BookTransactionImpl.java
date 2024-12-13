package com.bookrental.serviceimpl;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.RentType;
import com.bookrental.model.Book;
import com.bookrental.model.BookTransaction;
import com.bookrental.model.Member;
import com.bookrental.repository.BookRepo;
import com.bookrental.repository.BookTransactionRepo;
import com.bookrental.repository.MemberRepo;
import com.bookrental.service.BookTransactionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookTransactionImpl implements BookTransactionService {

	private final BookRepo bookRepo;

	private final MemberRepo memberRepo;

	private final BookTransactionRepo bookTransactionRepo;

	// Renting book Operation
	@Override
	@Transactional
	public boolean bookRentCreateAndUpdate(BookTransactionDto bookTransactionDto) {

		Member member = memberRepo.findById(bookTransactionDto.getMemberId()).orElseThrow(
				() -> new ResourceNotFoundException("MemberId", String.valueOf(bookTransactionDto.getMemberId())));

		Book book = bookRepo.findById(bookTransactionDto.getBookId()).orElseThrow(
				() -> new ResourceNotFoundException("BookId", String.valueOf(bookTransactionDto.getBookId())));

		BookTransaction rentedBook = bookTransactionRepo.findByMemberAndRentStatus(member, RentType.RENT);

//		Either RETURN the book or update the transaction made by changing the book
		if (rentedBook != null && bookTransactionDto.getId() != null) {

			BookTransaction tranBook = bookTransactionRepo.findById(bookTransactionDto.getId()).orElseThrow(
					() -> new ResourceNotFoundException("TransactionId", String.valueOf(bookTransactionDto.getId())));

			if (Boolean.TRUE.equals(bookTransactionDto.getToReturn())) {
				rentedBook.setRentStatus(RentType.RETURN);
				rentedBook.setActiveClosed(false);

				bookRepo.incrementBookStock(bookTransactionDto.getBookId(), 1);
				bookTransactionRepo.save(rentedBook);
			} else if (bookTransactionDto.getBookId() != null
					&& !bookTransactionDto.getBookId().equals(tranBook.getId())) {
				rentedBook.setBook(book);
				bookRepo.incrementBookStock(tranBook.getId(), 1);
				bookRepo.decrementBookStock(bookTransactionDto.getBookId());
				bookTransactionRepo.save(tranBook);
			}

		} else {
//		RENT new book
			int value = bookRepo.decrementBookStock(bookTransactionDto.getBookId());
			if (value < 1) {
				throw new ResourceNotFoundException("Book is out of stock.", null);
			}
			BookTransaction bookTransaction = BookTransaction.builder().code(UUID.randomUUID().toString())
					.fromDate(LocalDate.now()).toDate(LocalDate.now().plusDays(bookTransactionDto.getRentDuration()))
					.rentStatus(RentType.RENT).activeClosed(true).member(member).book(book).build();
			bookTransactionRepo.save(bookTransaction);
		}
		return true;
	}
}
