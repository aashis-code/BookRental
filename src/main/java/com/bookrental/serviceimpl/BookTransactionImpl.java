

package com.bookrental.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.dto.BookTransactionResponse;
import com.bookrental.dto.FilterRequest;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.helper.CustomPagination;
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

//		Book book = bookRepo.isBookAvailbleToRent(bookTransactionDto.getBookId()).orElseThrow(
//				() -> new ResourceNotFoundException("Book is not available to rent.", null));
		
		Book book;

		BookTransaction rentedBook = bookTransactionRepo.findByMemberAndRentStatus(member, RentType.RENT);

//		Either RETURN the book or update the transaction made by changing the book
		if (rentedBook != null && bookTransactionDto.getId() != null) {

			BookTransaction tranBook = bookTransactionRepo.findById(bookTransactionDto.getId()).orElseThrow(
					() -> new ResourceNotFoundException("TransactionId", String.valueOf(bookTransactionDto.getId())));

			if (Boolean.TRUE.equals(bookTransactionDto.getToReturn())) {
				rentedBook.setRentStatus(RentType.RETURN);
				rentedBook.setActiveClosed(false);

				bookRepo.incrementBookStock(bookTransactionDto.getBookId());
				bookTransactionRepo.save(rentedBook);
			} else if (bookTransactionDto.getBookId() != null
					&& !bookTransactionDto.getBookId().equals(tranBook.getId())) {
				
				book = bookRepo.isBookAvailbleToRent(bookTransactionDto.getBookId()).orElseThrow(()-> new ResourceNotFoundException("Book is not available to rent.", null));
				rentedBook.setBook(book);
				bookRepo.incrementBookStock(tranBook.getId());
				bookRepo.decrementBookStock(bookTransactionDto.getBookId());
				bookTransactionRepo.save(tranBook);
			}
			
		} else if(rentedBook != null && bookTransactionDto.getId() == null) {
			throw new ResourceAlreadyExist("You have already rented book before.", null);
		}
		else {
//		RENT new book
			book = bookRepo.isBookAvailbleToRent(bookTransactionDto.getBookId()).orElseThrow(()-> new ResourceNotFoundException("Book is not available to rent.", null));
			
			bookRepo.decrementBookStock(bookTransactionDto.getBookId());

			BookTransaction bookTransaction = BookTransaction.builder().code(UUID.randomUUID().toString())
					.fromDate(LocalDate.now()).toDate(LocalDate.now().plusDays(bookTransactionDto.getRentDuration()))
					.rentStatus(RentType.RENT).activeClosed(true).member(member).book(book).build();
			bookTransactionRepo.save(bookTransaction);
		}
		return true;
	}

	@Override
	public PaginatedResponse getPaginatedBookTransaction(FilterRequest filterRequest) {
		Map<String, Object> object = CustomPagination.getPaginatedObject(filterRequest);
		Page<BookTransaction> response = bookTransactionRepo.filterBookTransactionAndPagination((LocalDateTime)object.get("startDate"), (LocalDateTime)object.get("endDate"), (Boolean)Boolean.FALSE, (Integer)object.get("bookId"), (Integer)object.get("memberId"), (String)object.get("rentStatus"), (Pageable) object.get("pageable"));
		return PaginatedResponse.builder().content(response.getContent().stream().map(bookTran -> {
			BookTransactionResponse booktranRes = new BookTransactionResponse();
			CoustomBeanUtils.copyNonNullProperties(bookTran, booktranRes);
			return booktranRes;
		}).collect(Collectors.toList()))
				.totalElements(response.getTotalElements()).currentPageIndex(response.getNumber())
				.numberOfElements(response.getNumberOfElements()).totalPages(response.getTotalPages()).build();
	}
}
