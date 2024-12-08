package com.bookrental.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bookrental.dto.CategoryDto;
import com.bookrental.model.Category;

public class CategoryModelMapper {

	@Autowired
	private ModelMapper modelMapper;

	public Category categorDtoToCategory(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Category.class);
	}

	public CategoryDto categoryToCategoryDto(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}

}
