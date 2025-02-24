package com.bookrental.mapper.booktransaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTransactionDetails {

    private Long id;
    private String rentStatus;
    private String fromDate;
    private String toDate;
    private Long memberId;
    private Long bookId;
    private Long daysLeft;
    private String createdDate;
    private String publishedDate;
    private String lastModifiedDate;
    private String memberName;
    private String bookName;
}
