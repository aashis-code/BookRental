package com.bookrental.dto;

import java.time.LocalDate;
import java.util.List;

import com.bookrental.helper.RentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTransactionResponse {

    private Integer id;

    private String code;

    private LocalDate fromDate;

    private LocalDate toDate;

    @Enumerated(EnumType.STRING)
    private RentType rentStatus;

    private Boolean activeClosed;

    private List<MemberDto> member;

    private List<BookDto> book;
}
