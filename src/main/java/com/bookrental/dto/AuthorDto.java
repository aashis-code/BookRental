package com.bookrental.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {

	private Integer id;

	@Size(min = 3, max = 20, message = "Name must be 3 to 20 characters long.")
	private String name;

	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Enter valid email address.")
	private String email;

	@Size(min = 10, max = 10, message = "Phone number must consist 10 digits.")
	private String mobileNumber;
	
	private Boolean toDelete;

}
