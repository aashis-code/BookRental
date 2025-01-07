package com.bookrental.serviceimpl;

import com.bookrental.dto.*;
import com.bookrental.exceptions.AppException;
import com.bookrental.exceptions.FileUploadFailException;
import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.helper.ExcelHelper;
import com.bookrental.helper.pagination.BookPaginationRequest;
import com.bookrental.model.Author;
import com.bookrental.model.Book;
import com.bookrental.model.Category;
import com.bookrental.repository.AuthorRepo;
import com.bookrental.repository.BookRepo;
import com.bookrental.repository.CategoryRepo;
import com.bookrental.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookImpl implements BookService {

    private final AuthorRepo authorRepo;

    private final CategoryRepo categoryRepo;

    private final BookRepo bookRepo;

    @Override
    public boolean bookSaveAndUpdate(ListOfBookRequest bookAddRequests) {

        List<Book> books = new ArrayList<>();
        Map<Integer, Author> authorMap = new HashMap<>();
        Map<Integer, Category> categoryMap = new HashMap<>();

        for (BookAddRequest bookAddRequest : bookAddRequests.getBookAddRequest()) {
            compareDate(bookAddRequest.getPublishedDate());
            Set<Integer> authorIds = bookAddRequest.getAuthorId();
            for (Integer authorId : authorIds) {
                if (!authorMap.containsKey(authorId)) {
                    Author author = authorRepo.findByIdAndDeleted(authorId, Boolean.FALSE)
                            .orElseThrow(() -> new ResourceNotFoundException("AuthorId", String.valueOf(authorId)));
                    authorMap.put(author.getId(), author);
                }
            }
            if (!categoryMap.containsKey(bookAddRequest.getCategoryId())) {
                Category category = this.categoryRepo.findByIdAndDeleted(bookAddRequest.getCategoryId(), Boolean.FALSE)
                        .orElseThrow(() -> new ResourceNotFoundException("CategoryId",
                                String.valueOf(bookAddRequest.getCategoryId())));
                categoryMap.put(category.getId(), category);
            }

            if (bookAddRequest.getId() != null && bookAddRequest.getId() > 0) {
                Book book = bookRepo.findByIdAndDeleted(bookAddRequest.getId(), Boolean.FALSE).orElseThrow(
                        () -> new ResourceNotFoundException("BookId", String.valueOf(bookAddRequest.getId())));
                CoustomBeanUtils.copyNonNullProperties(bookAddRequest, book);
                books.add(updateAuthorAndCategory(book, bookAddRequest, authorMap, categoryMap));
                continue;
            }

            Book bookAddRequestToBook = new Book();
            String photoPath = savePicture(bookAddRequest.getPhoto());
            CoustomBeanUtils.copyNonNullProperties(bookAddRequest, bookAddRequestToBook);
            bookAddRequestToBook.setCategory(categoryMap.get(bookAddRequest.getCategoryId()));
            bookAddRequestToBook
                    .setAuthors(bookAddRequest.getAuthorId().stream().map(authorMap::get).collect(Collectors.toList()));
            bookAddRequestToBook.setPhoto(photoPath);
            books.add(bookAddRequestToBook);
        }

        List<Book> saveAll = bookRepo.saveAll(books);
        return !saveAll.isEmpty();
    }

    @Override
    public List<BookResponse> getAllBooks() {
        String[] fields = {"authorId", "categoryId"};
        List<Book> books = bookRepo.findAllByDeleted(Boolean.FALSE);
        List<BookResponse> bookResponse = new ArrayList<>();
        for (Book book : books) {
            BookResponse bookAdd = new BookResponse();
            BeanUtils.copyProperties(book, bookAdd, fields);

            bookAdd.setAuthorNames(book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()));
            bookAdd.setCategoryName(book.getCategory().getName());
            bookResponse.add(bookAdd);
        }
        return bookResponse;
    }


    @Override
    public PaginatedResponse getPaginatedBookList(BookPaginationRequest paginationRequest) {
        Page<Map<String, Object>> response = bookRepo.filterBookAndPagination(paginationRequest.getSearchFeild(), paginationRequest.getFromDate(), paginationRequest.getToDate(), paginationRequest.getIsDeleted(), paginationRequest.getOrderBy().toString(), paginationRequest.getPageable());
        return PaginatedResponse.builder().content(response.getContent())
                .totalElements(response.getTotalElements()).currentPageIndex(response.getNumber())
                .numberOfElements(response.getNumberOfElements()).totalPages(response.getTotalPages()).build();
    }

    @Override
    public Book getBookById(Integer bookId) {
        if (bookId < 1) {
            throw new AppException("Enter valid book Id.");
        }
        return bookRepo.findByIdAndDeleted(bookId, Boolean.FALSE)
                .orElseThrow(() -> new ResourceNotFoundException("BookId", String.valueOf(bookId)));
    }

    @Override
    public void deleteBook(Integer bookId) {
        if (bookId < 1) {
            throw new AppException("Invalid Book Id.");
        }
        int result = bookRepo.deleteBookById(bookId);
        if (result < 1) {
            throw new ResourceNotFoundException("BookId", String.valueOf(bookId));
        }
    }

    @Override
    public void getBookOnExcel(HttpServletResponse response) {
        List<BookResponse> books = getAllBooks();
        ByteArrayInputStream byteArrayInputStream;
        try {
            byteArrayInputStream = ExcelHelper.exportToExcel(books);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"books.xls\"");
            byteArrayInputStream.transferTo(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Book updateAuthorAndCategory(Book book, BookAddRequest bookAddRequest, Map<Integer, Author> authorMap, Map<Integer, Category> categoryMap) {
        List<Author> authors = new ArrayList<>();
        Set<Integer> authorIds = bookAddRequest.getAuthorId();
        Category category;
        for (Integer authorId : authorIds) {
            if (!authorMap.containsKey(authorId)) {
                Author author = authorRepo.findByIdAndDeleted(authorId, Boolean.FALSE).orElseThrow(() -> new ResourceNotFoundException("AuthorId", String.valueOf(authorId)));
                authors.add(author);
            } else {
                authors.add(authorMap.get(authorId));
            }
        }
        if (bookAddRequest.getCategoryId() != null && categoryMap.containsKey(bookAddRequest.getCategoryId())) {
            category = categoryRepo.findByIdAndDeleted(bookAddRequest.getCategoryId(), Boolean.FALSE).orElseThrow(() -> new ResourceNotFoundException("CategoryId", String.valueOf(bookAddRequest.getCategoryId())));
            categoryMap.put(bookAddRequest.getCategoryId(), category);
        }

        book.setAuthors(authors);
        book.setCategory(categoryMap.get(bookAddRequest.getCategoryId()));
        return book;
    }

    void compareDate(LocalDate date) {
        if (date != null && !date.isBefore(LocalDate.now())) {
            throw new ResourceAlreadyExist("Provided date is not valid.", null);
        }
    }

    @Override
    public List<BookDto> getBooksByCategory(Integer categoryId) {
        if (categoryId < 0) {
            throw new ResourceNotFoundException("Enter valid category.", null);
        }
        return bookRepo.findBooksByCategoryId(categoryId).stream().map(book -> {
            BookDto bookDto = new BookDto();
            CoustomBeanUtils.copyNonNullProperties(book, bookDto);
            return bookDto;
        }).toList();
    }

    public String savePicture(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new FileUploadFailException("File is missing.");
        }

        String originalFilename = file.getOriginalFilename();
        File f = new File("images");
        if (!f.exists()) {
            f.mkdir();
        }

        String uploadImagePath = "images" + File.separator + originalFilename;

        Path path = Paths.get(uploadImagePath);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadImagePath;
    }

    @Override
    public List<BookDto> getBooksByAuthor(Integer authorId) {
        Author author = authorRepo.findByIdAndDeleted(authorId, Boolean.FALSE).orElseThrow(() -> new ResourceNotFoundException("AuthorId", String.valueOf(authorId)));
        return bookRepo.findByAuthors(author).stream().map(book -> {
            BookDto bookDto = new BookDto();
            CoustomBeanUtils.copyNonNullProperties(book, bookDto);
            return bookDto;
        }).toList();
    }
}
