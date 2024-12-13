package com.bookrental.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bookrental.dto.BookAddRequest;
import com.bookrental.dto.ListOfBookRequest;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.helper.ExcelHelper;
import com.bookrental.model.Author;
import com.bookrental.model.Book;
import com.bookrental.model.Category;
import com.bookrental.repository.AuthorRepo;
import com.bookrental.repository.BookRepo;
import com.bookrental.repository.CategoryRepo;
import com.bookrental.service.BookService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookImpl implements BookService {

	private final AuthorRepo authorRepo;

	private final CategoryRepo categoryRepo;

	private final BookRepo bookRepo;

	@Override
	@Transactional
	public boolean bookSaveAndUpdate(ListOfBookRequest bookAddRequests) {

		List<Book> books = new ArrayList<>();
		Map<Integer, Author> authorMap = new HashMap<>();
		Map<Integer, Category> categoryMap = new HashMap<>();
//I think you should work on basis of ISBN
		for (BookAddRequest bookAddRequest : bookAddRequests.getBookAddRequest()) {

			Optional<Book> book = bookRepo.findByIsbn(bookAddRequest.getIsbn());
			if (bookAddRequest.getId() == null && book.isPresent() && bookAddRequest.getIsbn() != null
					&& bookAddRequest.getIsbn().equals(book.get().getIsbn())) {
				bookRepo.incrementBookStockByIsbn(bookAddRequest.getIsbn(), bookAddRequest.getStockCount());
				continue;
			}

			Set<Integer> authorIds = bookAddRequest.getAuthorId();
			for (Integer authorId : authorIds) {
				if (!authorMap.containsKey(authorId)) {
					Author author = authorRepo.findById(authorId)
							.orElseThrow(() -> new ResourceNotFoundException("AuthorId", String.valueOf(authorId)));
					authorMap.put(author.getId(), author);
				}
			}
			if (!categoryMap.containsKey(bookAddRequest.getCategoryId())) {
				Category category = this.categoryRepo.findById(bookAddRequest.getCategoryId())
						.orElseThrow(() -> new ResourceNotFoundException("CategoryId",
								String.valueOf(bookAddRequest.getCategoryId())));
				categoryMap.put(category.getId(), category);
			}

			if (bookAddRequest.getId() != null) {
				CoustomBeanUtils.copyNonNullProperties(bookAddRequest, book.get());
				books.add(book.get());
				continue;
			}

			Book bookAddRequestToBook = new Book();
			CoustomBeanUtils.copyNonNullProperties(bookAddRequest, bookAddRequestToBook);
			bookAddRequestToBook.setCategory(categoryMap.get(bookAddRequest.getCategoryId()));
			bookAddRequestToBook
					.setAuthors(bookAddRequest.getAuthorId().stream().map(authorMap::get).collect(Collectors.toList()));
			books.add(bookAddRequestToBook);
		}

		List<Book> saveAll = bookRepo.saveAll(books);
		return !saveAll.isEmpty();
	}

	@Override
	public List<BookAddRequest> getAllBooks() {
		String[] fields = { "authorId", "categoryId" };
		List<Book> books = bookRepo.findAll();
		List<BookAddRequest> bookAddRequest = new ArrayList<>();
		for (Book book : books) {
			BookAddRequest bookAdd = new BookAddRequest();
			BeanUtils.copyProperties(book, bookAdd, fields);

			bookAdd.setAuthorId(book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet()));
			bookAdd.setCategoryId(book.getCategory().getId());
			bookAddRequest.add(bookAdd);
		}
		return bookAddRequest;
	}

	@Override
	public Book getBookById(Integer bookId) {
		if (bookId < 1) {
			throw new ResourceNotFoundException("Enter valid book Id.", null);
		}
		return bookRepo.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("BookId", String.valueOf(bookId)));
	}

	@Override
	public boolean deleteBook(Integer bookId) {
		if (bookId < 1) {
			throw new ResourceNotFoundException("Invalid Book Id.", null);
		}
		Book book = bookRepo.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("BookId", String.valueOf(bookId)));
		bookRepo.delete(book);
		return true;
	}

	@Override
	public ByteArrayInputStream getBookOnExcel() {
		List<BookAddRequest> books = getAllBooks();
		ByteArrayInputStream byteArrayInputStream;
		try {
			byteArrayInputStream = ExcelHelper.exportToExcel(books);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return byteArrayInputStream;
	}

}
