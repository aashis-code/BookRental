package com.bookrental.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookAddRequest {

	private Integer id;

	@Size(min = 3, max = 20, message = "Book name must be 3 to 20 characters long.")
	private String name;

	@Min(value = 1, message = "Book must at least contain 1 page.")
	private Integer numberOfPages;

	private String isbn;

	private Double rating;

	@Size(min = 0, message = "No of books musn't be less than zero.")
	private Integer stockCount;

	private LocalDate publishedDate;

	private String photo;

	private Set<Integer> authorId;

	private Integer categoryId;

}
