package com.bookrental.dto;

import java.util.List;

import com.bookrental.validation.email.ValidEmail;
import com.bookrental.validation.mobile.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	
	private Integer id;

	@Schema(description = "Username of the Member", required = true, example = "john_doe")
    @ValidEmail
	private String email;

	@Size(min = 3, max = 20, message = "Name must be 3 to 20 characters long.")
	private String name;
	
	@Size(min = 5, max = 12, message = "Password must be 5 to 12 characters long.")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@ValidPhoneNumber
	private String mobileNumber;

	private String address;

	@JsonProperty(access = Access.WRITE_ONLY)
	private Boolean toDelete;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<String> roles;

}
