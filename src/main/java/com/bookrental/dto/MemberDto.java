package com.bookrental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	
	private Integer id;

	private String email;

	private String name;

	private String mobileNo;

	private String address;
	
	private Boolean toDelete;

}
