package com.bookrental.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bookrental.dto.BookTransactionDto;
import com.bookrental.model.BookTransaction;

public class BookTransactionModelMapper {

	@Autowired
	private ModelMapper modelMapper;

	public BookTransaction bookTransactionDtoToBookTransaction(BookTransactionDto bookTransactionDto) {
		return modelMapper.map(bookTransactionDto, BookTransaction.class);
	}

	public BookTransactionDto BookTransactionToCategoryDto(BookTransaction bookTransaction) {
		return modelMapper.map(bookTransaction, BookTransactionDto.class);
	}

}
