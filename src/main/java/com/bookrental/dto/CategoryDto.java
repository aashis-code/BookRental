package com.bookrental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

	private Integer id;

	private String name;

	private String description;
	
	private Boolean toDelete;
}
