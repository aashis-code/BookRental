package com.bookrental.dto;

import java.time.LocalDate;
import java.util.Set;

import com.bookrental.helper.constants.MessageConstants;
import com.bookrental.validation.date.ValidDate;
import com.bookrental.validation.isbn.ValidIsbn;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookAddRequest {

	private Integer id;

	@Size(min = 3, max = 30, message = "Book name must be 3 to 30 characters long.")
	private String name;

	@Min(value = 1, message = "Book must at least contain 1 page.")
	private Integer numberOfPages;

	@ValidIsbn(message = MessageConstants.INVALID_ISBN)
	private String isbn;

	@Min(value = 0, message = "Rating must be not less than zero.")
	private Double rating;

	@Min(value = 1, message = "No of books must not be less than zero.")
	private Integer stockCount;

	@ValidDate(message = MessageConstants.INVALID_DATE)
	private LocalDate publishedDate;

	private MultipartFile photo;

	private Set<Integer> authorId;

	private Integer categoryId;

}
