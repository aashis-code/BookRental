package com.bookrental.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaginatedResponse {
	
	Object content;
	
	Integer totalPages;
	
	Long totalElements;
	
	Integer numberOfElements;
	
	Integer currentPageIndex;

}
