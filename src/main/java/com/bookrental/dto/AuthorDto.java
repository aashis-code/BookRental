package com.bookrental.dto;

import com.bookrental.validation.email.ValidEmail;
import com.bookrental.validation.mobile.ValidPhoneNumber;
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

    //	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Enter valid email address.")
    @ValidEmail
    private String email;

    @ValidPhoneNumber
    private String mobileNumber;

    @JsonIgnore
    private Boolean toDelete;

}
