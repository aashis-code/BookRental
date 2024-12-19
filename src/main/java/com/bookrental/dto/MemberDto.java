package com.bookrental.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	
	private Integer id;

	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Enter valid email address.")
	private String email;

	@Size(min = 3, max = 20, message = "Name must be 3 to 20 characters long.")
	private String name;
	
	@Size(min = 5, max = 12, message = "Password must be 5 to 12 characters long.")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Size(min = 10, max = 10, message = "Phone number must consist 10 digits.")
	private String mobileNo;

	private String address;

	@JsonProperty(access = Access.WRITE_ONLY)
	private Boolean toDelete;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<String> roles;

}
