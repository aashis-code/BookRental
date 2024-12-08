package com.bookrental.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bookrental.dto.AuthorDto;
import com.bookrental.model.Author;

public class AuthorModelMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Author AuthorDtoToAuthor(AuthorDto autherDto) {
		return modelMapper.map(autherDto, Author.class);
	}
	
	public AuthorDto AuthorToAuthorDto(Author auther) {
		return modelMapper.map(auther, AuthorDto.class);
	}

}
