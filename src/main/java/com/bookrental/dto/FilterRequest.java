package com.bookrental.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.RentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterRequest {

	String keyword;

	Integer pageNumber;

	Integer pageSize;

	String orderBy;

	String sortDir;

	LocalDate startDate;

	LocalDate endDate;

	Integer bookId;

	Integer memberId;

	String rentStatus;

	
	public void setStartDate(LocalDate startDate) {
		if(startDate == null) {
			this.startDate = LocalDate.of(2001, 1, 1);
			return;
		}
		if(startDate.isAfter(LocalDate.now())) {
			throw new ResourceNotFoundException("Invalid start date.", null);
		}
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		if(endDate == null) {
			this.endDate= LocalDate.now();
			return;
		}
		if(endDate.isAfter(LocalDate.now().plusDays(1))) {
			throw new ResourceNotFoundException("Invalid end date.", null);
		}
		this.endDate = endDate;
	}
	
	
	public void setRentStatus(String rentType) {
		if(rentType != null) {
			if(rentType.equalsIgnoreCase(RentType.RENT.toString())) {
				this.rentStatus=rentType.toUpperCase();
			}
			if(rentType.equalsIgnoreCase(RentType.RETURN.toString())) {
				this.rentStatus=rentType.toUpperCase();
			}
			throw new ResourceNotFoundException("Rent Satus", rentType);
		}
	}
	

}
