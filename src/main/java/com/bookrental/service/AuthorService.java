package com.bookrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.AuthorDto;
import com.bookrental.model.Author;

@Service
public interface AuthorService {
	
	boolean authorOperation(AuthorDto authorDto);
	
	Author getAuthorById(Integer authorId);
	
	List<Author> getAllAuthors();

}
