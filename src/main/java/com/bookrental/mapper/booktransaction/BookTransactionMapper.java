package com.bookrental.mapper.booktransaction;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BookTransactionMapper {

    @Select(value = "select bt.id,bt.rent_status as rentStatus,bt.from_date as fromDate,m.id as memberId ,b.id as bookId , bt.to_date as toDate ,(bt.to_date::DATE-CURRENT_DATE::DATE) as daysLeft,\n" +
            "            to_char(bt.created_date, 'YYYY-MM-DD HH24:MI:SS') as createdDate, to_char(b.published_date, 'YYYY-MM-DD HH24:MI:SS') as publishedDate,\n" +
            "            to_char(bt.modified_date, 'YYYY-MM-DD HH24:MI:SS') as lastModifiedDate, m.name as memberName, \n" +
            "            b.name as bookName from book_transaction bt inner join member m on bt.member_id=m.id inner join book b on bt.book_id=b.id \n" +
            "            where bt.created_date between coalesce (#{startDate} , bt.created_date) and \n" +
            "            coalesce (#{endDate} , bt.created_date) and (#{deleted} is null or bt.deleted = #{deleted}) and \n" +
            "            (#{bookId}=-1 or bt.book_id = #{bookId}) and  (#{memberId}=-1 or bt.member_id= #{memberId}) \n" +
            "            and (#{rentStatus}='' or bt.rent_status= #{rentStatus})  limit #{size} offset #{offset}")
    List<BookTransactionDetails> filterBookTransaction(
            LocalDate startDate,
            LocalDate endDate,
            Boolean deleted,
            Integer bookId,
            Integer memberId,
            String rentStatus,
            Integer offset,
            Integer size);


}
