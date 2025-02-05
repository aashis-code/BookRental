package com.bookrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookDto {
	
	private Integer id;

	private String name;

	private Integer numberOfPages;

	private String isbn;

	private Double rating;

	private Integer stockCount;

	private LocalDate publishedDate;

	private String photo;

}
