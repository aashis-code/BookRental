package com.bookrental.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookAddRequest {

	private Integer id;

	private String name;

	private Integer numberOfPages;

	private String isbn;

	private Double rating;

	private Integer stockCount;

	private LocalDate publishedDate;

	private String photo;

	private Set<Integer> authorId;

	private Integer categoryId;

}
