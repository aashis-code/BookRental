package com.bookrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.AuthorDto;
import com.bookrental.dto.FilterRequest;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.model.Author;
import com.bookrental.model.Book;

@Service
public interface AuthorService {
	
	boolean saveAndUpdateAuthor(AuthorDto authorDto);
	
	Author getAuthorById(Integer authorId);
	
	List<Author> getAllAuthors();
	
	void deleteAuthor(Integer authorId);
	
	PaginatedResponse getPaginatedAuthorList(FilterRequest filterRequest);
	
	
	
}
