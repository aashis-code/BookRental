package com.bookrental.serviceimpl;

import com.bookrental.dto.AuthorDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.exceptions.AppException;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.model.Author;
import com.bookrental.repository.AuthorRepo;
import com.bookrental.service.AuthorService;
import com.bookrental.validation.Update;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthorImpl implements AuthorService {

    private AuthorRepo authorRepo;

    private final Validator validator;
    private Logger logger = LoggerFactory.getLogger(AuthorImpl.class);

    public AuthorImpl(AuthorRepo authorRepo, Validator validator) {
        this.authorRepo = authorRepo;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
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
    public PaginatedResponse getPaginatedAuthorList(PaginationRequest paginationRequest) {
        Page<Map<String, Object>> response = authorRepo.filterAuthorPaginated(paginationRequest.getSearchField(), paginationRequest.getFromDate(), paginationRequest.getToDate(), paginationRequest.getIsDeleted(), paginationRequest.getPageable());
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
        if (result < 1) {
            throw new ResourceNotFoundException("AuthorId", String.valueOf(authorId));
        }
    }

}
