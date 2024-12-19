package com.bookrental.dto;

import java.time.LocalDate;
import java.util.Optional;

import com.bookrental.helper.RentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterRequest {

	String searchField;
	
	String keyword;

	Integer pageNumber;

	Integer pageSize;

	String orderBy;

	String sortDir;

	LocalDate startDate;

	LocalDate endDate;
	
	Integer bookId;
	
	Integer memberId;
	
	@Enumerated(EnumType.STRING)
	RentType rentStatus;

	
}
