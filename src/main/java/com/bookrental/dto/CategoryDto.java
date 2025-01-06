package com.bookrental.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

	private Integer id;

	@Size(min = 3, message = "The name must be at least 3 characters long.")
	private String name;

	@Size(min = 10, message = "The description must be at least 10 characters long")
	private String description;

	private Boolean toDelete;
}
