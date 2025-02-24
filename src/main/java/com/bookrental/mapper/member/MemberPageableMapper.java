package com.bookrental.mapper.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MemberPageableMapper {

    @Select(value = "select id, name, email, mobile_number, address , created_by , to_char(created_date, 'YYYY-MM-DD HH24:MI:SS') as created_date, "
            + "to_char(modified_date, 'YYYY-MM-DD HH24:MI:SS') as last_modified_date from member where "
            + "(#{keyword} is null or name ilike concat('%',#{keyword},'%')) "
            + "and created_date between coalesce(#{startDate}, created_date) and coalesce(#{endDate}, created_date) and (#{deleted} is null or deleted = #{deleted}) "
            + " limit #{page} offset #{offset} ")
    List<MemberResponse> filterMemberPaginated(
            @Param("keyword") String keyword,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("deleted") Boolean deleted,
            @Param("page") Integer page,
            @Param("offset") Integer offset);

}
