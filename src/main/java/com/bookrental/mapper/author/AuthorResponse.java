package com.bookrental.mapper.author;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResponse {

    private int id;

    private String name;

    private String email;

    private String mobileNumber;
}
