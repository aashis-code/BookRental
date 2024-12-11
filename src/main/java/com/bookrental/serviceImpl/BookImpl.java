package com.bookrental.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookrental.dto.BookAddRequest;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.model.Author;
import com.bookrental.model.Book;
import com.bookrental.model.Category;
import com.bookrental.modelmapper.BookModelMapper;
import com.bookrental.repository.AuthorRepo;
import com.bookrental.repository.BookRepo;
import com.bookrental.repository.CategoryRepo;
import com.bookrental.service.BookService;

@Component
public class BookImpl implements BookService {

	private final BookModelMapper bookModelMapper;

	private final AuthorRepo authorRepo;

	private final CategoryRepo categoryRepo;

	private final BookRepo bookRepo;
	
	public BookImpl(BookModelMapper bookModelMapper, AuthorRepo authorRepo, CategoryRepo categoryRepo, BookRepo bookRepo) {
		this.bookModelMapper=bookModelMapper;
		this.authorRepo=authorRepo;
		this.categoryRepo=categoryRepo;
		this.bookRepo=bookRepo;
	}

	@Override
	public boolean bookCUDOperation(List<BookAddRequest> bookAddRequests) {
		List<Book> books = new ArrayList<Book>();
		for (BookAddRequest bookAddRequest : bookAddRequests) {
			int categoryId = bookAddRequest.getCategoryId();
			Category category = categoryRepo.findById(categoryId)
					.orElseThrow(() -> new ResourceNotFoundException("CategoryId", String.valueOf(categoryId)));

			List<Author> authors = new ArrayList<Author>();
			Set<Integer> authorIds = bookAddRequest.getAuthorId();
			for (Integer authorId : authorIds) {
				Author author = authorRepo.findById(authorId)
						.orElseThrow(() -> new ResourceNotFoundException("Author", String.valueOf(authorId)));
				authors.add(author);
			}

			Book bookAddRequestToBook = bookModelMapper.BookAddRequestToBook(bookAddRequest);
			bookAddRequestToBook.setCategory(category);
			bookAddRequestToBook.setAuthors(authors);
			books.add(bookAddRequestToBook);
		}

		List<Book> saveAll = bookRepo.saveAll(books);
		if (saveAll == null) {
			return false;
		}

		return true;
	}

	@Override
	public List<BookAddRequest> getAllBooks() {
		String fields[] = { "authorId", "categoryId" };
		List<Book> books = bookRepo.findAll();
		List<BookAddRequest> bookAddRequest = new ArrayList<>();
		for (Book book : books) {
			BookAddRequest bookAdd = new BookAddRequest();
			BeanUtils.copyProperties(book, bookAdd, fields);

			bookAdd.setAuthorId(book.getAuthors().stream().map(bk -> bk.getId()).collect(Collectors.toSet()));
			bookAdd.setCategoryId(book.getCategory().getId());
			bookAddRequest.add(bookAdd);
		}
		return bookAddRequest;
	}
	
	public Book getBookById(Integer bookId) {
		if(bookId<1) {
			throw new ResourceNotFoundException("Enter valid book Id.", null);
		}
		
		return bookRepo.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("BookId", String.valueOf(bookId)));
	}

}
