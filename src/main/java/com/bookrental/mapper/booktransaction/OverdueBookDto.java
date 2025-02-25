package com.bookrental.mapper.booktransaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OverdueBookDto {
    private Long bookId;
    private String bookName;
    private String isbn;
    private String borrower;
    private String email;
    private Integer daysOverdue;
}
