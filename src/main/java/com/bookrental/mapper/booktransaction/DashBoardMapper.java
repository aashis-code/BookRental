package com.bookrental.mapper.booktransaction;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DashBoardMapper {


    @Select("SELECT \n" +
            "    -- Total number of books issued\n" +
            "    (SELECT COUNT(*) FROM book_transaction) AS totalBookIssued,\n" +
            "\n" +
            "    -- Total books that have been returned\n" +
            "    (SELECT COUNT(*) FROM book_transaction WHERE rent_status = 'RETURN') AS totalBooksReturned,\n" +
            "\n" +
            "    -- Total overdue books (not returned and past due date)\n" +
            "    (SELECT COUNT(*) FROM book_transaction \n" +
            "     WHERE to_date < CURRENT_DATE AND rent_status != 'RETURNED') AS totalOverdueBooks, \n" +
            "\n" +
            "    -- Total unique borrowers\n" +
            "    (SELECT COUNT(DISTINCT member_id) FROM book_transaction) AS uniqueBorrowers,\n" +
            "\n" +
            "    -- Active transactions (books currently rented)\n" +
            "    (SELECT COUNT(*) FROM book_transaction WHERE rent_status != 'RETURN') AS activeTransaction,\n" +
            "\n" +
            "    -- List of overdue books\n" +
            "    (SELECT jsonb_agg(t) \n" +
            "     FROM (\n" +
            "        SELECT \n" +
            "            bt.book_id as \"bookId\", \n" +
            "            b.name AS \"bookName\", \n" +
            "            b.isbn, \n" +
            "            m.name AS borrower, \n" +
            "            m.email, \n" +
            "            AGE(CURRENT_DATE, bt.to_date) AS \"daysOverdue\" \n" +
            "        FROM book_transaction bt\n" +
            "        LEFT JOIN book b ON b.id = bt.book_id\n" +
            "        LEFT JOIN member m ON bt.member_id = m.id  \n" +
            "        WHERE to_date < CURRENT_DATE AND rent_status = 'RENT' \n" +
            "        ORDER BY AGE(CURRENT_DATE, to_date) DESC\n" +
            "    ) t) AS overDueBookList,\n" +
            "    (SELECT jsonb_agg(t) \n" +
            "     FROM (\n" +
            "        SELECT \n" +
            "            bt.book_id, \n" +
            "            b.name AS bookName, \n" +
            "            b.isbn, \n" +
            "            m.name AS borrower, \n" +
            "            m.email, \n" +
            "            AGE(CURRENT_DATE, bt.to_date) AS daysOverdue\n" +
            "        FROM book_transaction bt\n" +
            "        LEFT JOIN book b ON b.id = bt.book_id\n" +
            "        LEFT JOIN member m ON bt.member_id = m.id  \n" +
            "        ORDER BY bt.created_date DESC \n" +
            "        LIMIT 10\n" +
            "    ) t) AS recentBookTransaction,\n" +
            "\n" +
            "    -- Books borrowed per day\n" +
            "    (SELECT jsonb_agg(t) \n" +
            "     FROM (\n" +
            "        SELECT \n" +
            "            TO_CHAR(created_date, 'YYYY-MM-DD') AS issuedDate, \n" +
            "            COUNT(*) AS bookIssued\n" +
            "        FROM book_transaction \n" +
            "        GROUP BY TO_CHAR(created_date, 'YYYY-MM-DD') \n" +
            "        ORDER BY TO_CHAR(created_date, 'YYYY-MM-DD')\n" +
            "    ) t) AS booksBorrowedPerDay,\n" +
            "\n" +
            "    -- Top borrowers (by count of books borrowed)\n" +
            "    (SELECT jsonb_agg(t) \n" +
            "     FROM (\n" +
            "        SELECT \n" +
            "            m.name, \n" +
            "            m.email, \n" +
            "            COUNT(*) AS booksBorrowed \n" +
            "        FROM book_transaction bt\n" +
            "        LEFT JOIN member m ON m.id = bt.member_id \n" +
            "        GROUP BY m.id \n" +
            "        ORDER BY booksBorrowed DESC \n" +
            "        LIMIT 10\n" +
            "    ) t) AS topBorrowers,\n" +
            "\n" +
            "    -- Most borrowed books (by count of times borrowed)\n" +
            "    (SELECT jsonb_agg(t) \n" +
            "     FROM (\n" +
            "        SELECT \n" +
            "            bt.book_id AS bookId, \n" +
            "            b.name as bookName, \n" +
            "            b.published_date as publishedDate, \n" +
            "            COUNT(*) AS timesBorrowed \n" +
            "        FROM book_transaction bt\n" +
            "        LEFT JOIN book b ON bt.book_id = b.id \n" +
            "        GROUP BY bt.book_id, b.name, b.published_date \n" +
            "        ORDER BY timesBorrowed DESC \n" +
            "        LIMIT 10\n" +
            "    ) t) AS mostBorrowedBook \n")
    DashBoardBookTransaction getBookDashBoard();

}
