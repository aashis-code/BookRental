package com.bookrental.serviceimpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.bookrental.dto.AuthorDto;
import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.model.Author;
import com.bookrental.repository.AuthorRepo;
import com.bookrental.service.AuthorService;

@Component
public class AuthorImpl implements AuthorService {

	private AuthorRepo authorRepo;

	public AuthorImpl(AuthorRepo authorRepo) {
		this.authorRepo = authorRepo;
	}

	@Override
	public boolean authorOperation(AuthorDto authorDto) {
		
		List<Author> authors;

		if (authorDto.getId() != null) {

			Author author = authorRepo.findByIdAndDeleted(authorDto.getId(), Boolean.FALSE)
					.orElseThrow(() -> new ResourceNotFoundException("AuthorId", String.valueOf(authorDto.getId())));
            
			if (!author.getEmail().equalsIgnoreCase(authorDto.getEmail()) || !author.getMobileNumber().equals(authorDto.getMobileNumber())) {
				authors = authorRepo.findByEmailOrPhoneNumber(authorDto.getEmail().toLowerCase(), authorDto.getMobileNumber(), Boolean.FALSE);
				
				if(!author.getEmail().equalsIgnoreCase(authorDto.getEmail())) {
					checkEmailAlreadExist(authors, authorDto);	
				}
				
				if(!author.getMobileNumber().equals(authorDto.getMobileNumber())) {
					checkPhoneNumberAlreadyExist(authors, authorDto);	
				}	
			}
			
			CoustomBeanUtils.copyNonNullProperties(authorDto, author);
			authorRepo.save(author);
			
		} else {
			authors = authorRepo.findByEmailOrPhoneNumber(authorDto.getEmail().toLowerCase(), authorDto.getMobileNumber(), Boolean.FALSE);
			checkEmailAlreadExist(authors, authorDto);
			checkPhoneNumberAlreadyExist(authors, authorDto);	
			Author author = new Author();
			CoustomBeanUtils.copyNonNullProperties(authorDto, author);
			author.setEmail(author.getEmail().toLowerCase());
			authorRepo.save(author);
		}
		return true;
	}

	@Override
	public Author getAuthorById(Integer authorId) {
		if (authorId < 1) {
			throw new ResourceNotFoundException("Invalid author Id.", null);
		}
		return authorRepo.findByIdAndDeleted(authorId, Boolean.FALSE)
				.orElseThrow(() -> new ResourceNotFoundException("AuthorId", String.valueOf(authorId)));
	}

	@Override
	public List<Author> getAllAuthors() {

		return authorRepo.findByDeleted(Boolean.FALSE);
	}

	@Override
	public boolean deleteAuthor(Integer authorId) {
		if (authorId < 1) {
			throw new ResourceNotFoundException("Invalid author Id.", null);
		}
		authorRepo.findByIdAndDeleted(authorId, Boolean.FALSE)
				.orElseThrow(() -> new ResourceNotFoundException("AuthorId", String.valueOf(authorId)));
		authorRepo.deleteAuthorById(authorId);
		return true;
	}
	
	// check whether email and phone number already exist in database before update
	public void checkEmailAlreadExist(List<Author> authors, AuthorDto authorDto) {
		
		for (Author aut : authors) {
			if (authorDto.getEmail().equalsIgnoreCase(aut.getEmail())) {
				throw new ResourceAlreadyExist("Email is already used.", null);
			}
		}
	}
	
	public void checkPhoneNumberAlreadyExist(List<Author> authors, AuthorDto authorDto) {
		for (Author aut : authors) {
			if (authorDto.getMobileNumber().equals(aut.getMobileNumber())) {
				throw new ResourceAlreadyExist("Phone Number is already used.", null);
			}
		}
	}

}
