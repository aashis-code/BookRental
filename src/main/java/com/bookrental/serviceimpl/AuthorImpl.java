package com.bookrental.serviceimpl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.bookrental.dto.AuthorDto;
import com.bookrental.dto.FilterRequest;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.model.Author;
import com.bookrental.repository.AuthorRepo;
import com.bookrental.service.AuthorService;

import jakarta.transaction.Transactional;

@Component
public class AuthorImpl implements AuthorService {

	private AuthorRepo authorRepo;

	public AuthorImpl(AuthorRepo authorRepo) {
		this.authorRepo = authorRepo;
	}

	@Override
	public boolean saveAndUpdateAuthor(AuthorDto authorDto) {
		Author author;
		if (authorDto.getId() != null) {
			author = authorRepo.findByIdAndDeleted(authorDto.getId(), Boolean.FALSE)
					.orElseThrow(() -> new ResourceNotFoundException("AuthorId", String.valueOf(authorDto.getId())));
			CoustomBeanUtils.copyNonNullProperties(authorDto, author);
			authorRepo.save(author);
		} else {
			author = new Author();
			CoustomBeanUtils.copyNonNullProperties(authorDto, author);
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
	public PaginatedResponse getPaginatedAuthorList(FilterRequest filterRequest) {
		Sort sort = Sort.by(Sort.Direction.fromString(filterRequest.getSortDir()),filterRequest.getOrderBy());
		Pageable pageable = PageRequest.of(filterRequest.getPageNumber(), filterRequest.getPageSize(), sort);
		Page<Map<String, Object>> response = authorRepo.filterAuthorPaginated(filterRequest.getKeyword(), filterRequest.getStartDate(), filterRequest.getEndDate(), Boolean.FALSE, pageable);
		return PaginatedResponse.builder().content(response.getContent())
				.totalElements(response.getTotalElements()).currentPageIndex(response.getNumber())
				.numberOfElements(response.getNumberOfElements()).totalPages(response.getTotalPages()).build();
	}

	@Override
	@Transactional
	public void deleteAuthor(Integer authorId) {
		if (authorId < 1) {
			throw new ResourceNotFoundException("Invalid author Id.", null);
		}
		int result = authorRepo.deleteAuthorById(authorId);
		if(result <1) {
			throw new ResourceNotFoundException("AuthorId", String.valueOf(authorId));
		}
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
