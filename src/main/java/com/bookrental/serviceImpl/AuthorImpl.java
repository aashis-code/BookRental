package com.bookrental.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookrental.dto.AuthorDto;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.model.Author;
import com.bookrental.modelmapper.AuthorModelMapper;
import com.bookrental.repository.AuthorRepo;
import com.bookrental.service.AuthorService;

@Component
public class AuthorImpl implements AuthorService {

	@Autowired
	private AuthorRepo authorRepo;

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

}
