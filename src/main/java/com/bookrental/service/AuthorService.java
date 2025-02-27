package com.bookrental.service;

import com.bookrental.dto.AuthorDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {

    boolean saveAndUpdateAuthor(AuthorDto authorDto);

    Author getAuthorById(Integer authorId);

    List<Author> getAllAuthors();

    void deleteAuthor(Integer authorId);

    PaginatedResponse getPaginatedAuthorList(PaginationRequest paginationRequest);

}
