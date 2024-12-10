package com.bookrental.dto;

import java.time.LocalDate;

import com.bookrental.helper.RentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTransactionDto {

	private Integer id;

	private String code;

	private LocalDate fromDate;

	private LocalDate toDate;

	private RentType rentStatus;
	
	private Boolean toReturn;

}
