package com.bookrental.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Pattern(regexp = "^[0-9]{10}$", message = "Enter valid number.")
	private String mobileNumber;

	@JsonIgnore
	private Boolean toDelete;

}
