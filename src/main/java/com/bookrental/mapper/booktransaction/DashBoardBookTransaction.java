package com.bookrental.mapper.booktransaction;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DashBoardBookTransaction {

    private int totalBookIssued;
    private int totalBooksReturned;
    private int totalOverdueBooks;
    private int uniqueBorrowers;
    private int activeTransaction;
    private String overDueBookList;
    private String recentBookTransaction;
    private String booksBorrowedPerDay;
    private String topBorrowers;
    private String mostBorrowedBook;
}

//@Data
//@NoArgsConstructor
//class OverdueBookDto {
//    private Long bookId;
//    private String bookName;
//    private String isbn;
//    private String borrower;
//    private String email;
//    private int daysOverdue;
//}

@Data
@NoArgsConstructor
class RecentTransactionDto {
    private Long bookId;
    private String bookName;
    private String isbn;
    private String borrower;
    private String email;
    private int daysOverdue;
}

@Data
@NoArgsConstructor
class BookBorrowedPerDayDto {
    private LocalDate issuedDate;
    private int bookIssued;
}

@Data
@NoArgsConstructor
class TopBorrowerDto {
    private String name;
    private String email;
    private int booksBorrowed;
}

@Data
@NoArgsConstructor
class MostBorrowedBookDto {
    private Long bookId;
    private String name;
    private LocalDate publishedDate;
    private int timesBorrowed;
}

