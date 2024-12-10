package com.bookrental.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseObject {
	
	private Boolean status;
	
	private String message;
	
	private Object data;


}
