package com.bookrental.mapper.author;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AuthorMapper {

    @Select("select id, name, email, mobile_number as mobileNumber from author where id = #{authorId} and deleted = #{deleted}")
    AuthorResponse findAuthorByIdAndDeleted(Integer authorId, Boolean deleted);

    @Select("select id, name, email, mobile_number as mobileNumber from author where deleted = false")
    List<AuthorResponse> getNonPageableAuthor();
}
