package com.bookrental.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private MemberRepo memberRepo;

	@Autowired
	private BookTransactionRepo bookTransactionRepo;

//	Renting book
	@Override
	public boolean bookRentOperation(Integer memberId, Integer bookId, Boolean returnBook) {

		Member member = memberRepo.findById(memberId)
				.orElseThrow(() -> new ResourceNotFoundException("Member", String.valueOf(memberId)));
		
		List<BookTransaction> rentedBooks = bookTransactionRepo.findByMemberAndRentStatus(member, RentType.RENT);
		
		Book book = bookRepo.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("BookId", String.valueOf(bookId)));

		
		if (!rentedBooks.isEmpty()) {
			
			if(Boolean.TRUE.equals(returnBook)) {
				BookTransaction bookTran = rentedBooks.get(0);
				bookTran.setRentStatus(RentType.RETURN);
				bookTran.setActiveClosed(false);
				
				bookRepo.bookStockIncrement(bookId);
				bookTransactionRepo.save(bookTran);
				return true;
			}
			
			throw new ResourceAlreadyExist("Rented Book", String.valueOf(bookId));

		}


		int value = bookRepo.bookStockDecrement(bookId);
		if (value < 1) {
			return false;
		}
		BookTransaction bookTransaction = BookTransaction.builder().code(UUID.randomUUID().toString()).fromDate(LocalDate.now())
				.toDate(LocalDate.now().plusDays(6)).rentStatus(RentType.RENT).activeClosed(true).member(member)
				.book(book).build();
		bookTransactionRepo.save(bookTransaction);
		return true;
	}

	

}
