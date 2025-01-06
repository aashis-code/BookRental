package com.bookrental.dto;

import java.util.List;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListOfBookRequest {
	
	@Valid
	private List<BookAddRequest> bookAddRequest;

}
