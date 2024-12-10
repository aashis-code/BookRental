package com.bookrental.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookrental.dto.CategoryDto;
import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.model.Category;
import com.bookrental.modelmapper.CategoryModelMapper;
import com.bookrental.repository.CategoryRepo;
import com.bookrental.service.CategoryService;

@Component
public class CategoryImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private CategoryModelMapper categoryModelMapper;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		if(categoryDto.getId() != null) {
			Optional<Category> category = categoryRepo.findById(categoryDto.getId());
			if (category.isPresent()) {
				throw new ResourceAlreadyExist("Id", String.valueOf(categoryDto.getId()));
			}
		}
		
		Category categoryValue = categoryModelMapper.categorDtoToCategory(categoryDto);
		Category savedCategory = categoryRepo.save(categoryValue);

		return categoryModelMapper.categoryToCategoryDto(savedCategory);
	}

}
