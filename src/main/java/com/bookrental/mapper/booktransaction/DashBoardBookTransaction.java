package com.bookrental.mapper.booktransaction;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class DashBoardBookTransaction {

    private int totalBookIssued;
    private int totalBooksReturned;
    private int totalOverdueBooks;
    private int uniqueBorrowers;
    private int activeTransaction;
    private List<OverdueBookDto> overDueBookList;
    private List<RecentTransactionDto> recentBookTransaction;
    private List<BookBorrowedPerDayDto> booksBorrowedPerDay;
    private List<TopBorrowerDto> topBorrowers;
    private List<MostBorrowedBookDto> mostBorrowedBook;
}

@Data
@NoArgsConstructor
class OverdueBookDto {
    private Long bookId;
    private String bookName;
    private String isbn;
    private String borrower;
    private String email;
    private int daysOverdue;
}

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

