package com.bookrental.mapper.booktransaction;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BookTransactionMapper {

    @Select(value = "select bt.id,bt.rent_status as \"rentStatus\",bt.from_date as \"fromDate\",m.id as \"memberId\" ,b.id as \"bookId\" , bt.to_date as \"toDate\" ,(bt.to_date::DATE-CURRENT_DATE::DATE) as \"daysLeft\",\r\n"
            + "to_char(bt.created_date, 'YYYY-MM-DD HH24:MI:SS') as \"createdDate\", to_char(b.published_date, 'YYYY-MM-DD HH24:MI:SS') as \"publishedDate\",\r\n"
            + "to_char(bt.modified_date, 'YYYY-MM-DD HH24:MI:SS') as \"lastModifiedDate\", m.name as \"memberName\", \r\n"
            + "b.name as \"bookName\" from book_transaction bt inner join member m on bt.member_id=m.id inner join book b on bt.book_id=b.id \n"
            + "where bt.created_date between coalesce (#{startDate} , bt.created_date) and "
            + "coalesce (#{endDate}, bt.created_date) and (#{deleted} is null or bt.deleted =#{deleted}) and "
            + "(#{bookId}::INTEGER is null or bt.book_id = #{bookId}::INTEGER) and  (#{memberId} is null or bt.member_id=#{memberId}) "
            + "and (#{rentStatus} is null or bt.rent_status=CAST(#{rentStatus} AS TEXT)) offset #{offset}::INTEGER limit #{size}::INTEGER ")
    List<BookTransactionDetails> filterBookTransaction(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("deleted") Boolean deleted,
            @Param("bookId") Integer bookId,
            @Param("memberId") Integer memberId,
            @Param("rentStatus") String rentStatus,
            @Param("offset") Integer offset,
            @Param("size") Integer size);


}
