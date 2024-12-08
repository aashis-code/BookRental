package com.bookrental.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookrental.dto.AuthorDto;
import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.model.Author;
import com.bookrental.modelmapper.AuthorModelMapper;
import com.bookrental.repository.AuthorRepo;
import com.bookrental.service.AuthorService;

@Component
public class AuthorImpl implements AuthorService {
	
	@Autowired
	private AuthorRepo authorRepo;
	
	@Autowired
	private AuthorModelMapper authorModelMapper;

	@Override
	public AuthorDto addAuthor(AuthorDto authorDto) {
		String email = authorDto.getEmail();
		
		Optional<Author> findByEmail = authorRepo.findByEmail(email);
		if(findByEmail.isPresent()) {
			throw new ResourceAlreadyExist("Email", email);
		}
		
		Author author = authorModelMapper.AuthorDtoToAuthor(authorDto);
		Author savedAuthor = authorRepo.save(author);
		
		return authorModelMapper.AuthorToAuthorDto(savedAuthor);
	}

}
