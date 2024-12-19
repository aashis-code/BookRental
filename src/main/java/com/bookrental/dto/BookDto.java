package com.bookrental.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

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
