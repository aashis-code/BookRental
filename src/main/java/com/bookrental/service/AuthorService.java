package com.bookrental.service;

import org.springframework.stereotype.Service;

import com.bookrental.dto.AuthorDto;

@Service
public interface AuthorService {
	
	boolean authorOperation(AuthorDto authorDto);

}
