package com.bookrental.serviceImpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.bookrental.dto.AuthorDto;
import com.bookrental.exceptions.ResourceNotFoundException;
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

		if (authorDto.getId() != null) {
			Author author = authorRepo.findById(authorDto.getId())
					.orElseThrow(() -> new ResourceNotFoundException("AuthorId", String.valueOf(authorDto.getId())));
			if (Boolean.TRUE.equals(authorDto.getToDelete())) {
				authorRepo.delete(author);
				return true;
			}
			BeanUtils.copyProperties(authorDto, author, "id");
			authorRepo.save(author);
			return true;
		} else {
			Author author = new Author();
			BeanUtils.copyProperties(authorDto, author, "id");
			authorRepo.save(author);
			return true;
		}

	}

	@Override
	public Author getAuthorById(Integer authorId) {
		if(authorId<1) {
			throw new ResourceNotFoundException("Invalid author Id.", null);
		}
		return authorRepo.findById(authorId).orElseThrow(()-> new ResourceNotFoundException("AuthorId", String.valueOf(authorId)));
	}

	@Override
	public List<Author> getAllAuthors() {

		return authorRepo.findAll();
	}

}
