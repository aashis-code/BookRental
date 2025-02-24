package com.bookrental.mapper.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {

    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private String address;
    private String createdBy;
    private String createdDate;
    private String lastModifiedDate;
}
