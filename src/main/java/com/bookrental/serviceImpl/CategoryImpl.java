package com.bookrental.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bookrental.dto.CategoryDto;
import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.model.Category;
import com.bookrental.modelmapper.CategoryModelMapper;
import com.bookrental.repository.CategoryRepo;
import com.bookrental.service.CategoryService;

@Component
public class CategoryImpl implements CategoryService {

	private final CategoryRepo categoryRepo;


	public CategoryImpl(CategoryRepo categoryRepo) {
		this.categoryRepo = categoryRepo;
	}

	@Override
	public boolean categoryCUD(CategoryDto categoryDto) {
		if (categoryDto.getId() != null) {
			Category category = categoryRepo.findById(categoryDto.getId()).orElseThrow(
					() -> new ResourceNotFoundException("CategoryId", String.valueOf(categoryDto.getId())));
			if (Boolean.TRUE.equals(categoryDto.getToDelete())) {
				categoryRepo.delete(category);
				return true;
			}
			CoustomBeanUtils.copyNonNullProperties(categoryDto, category);
			categoryRepo.save(category);
			return true;
		} else {
			Category category = new Category();
			CoustomBeanUtils.copyNonNullProperties(categoryDto, category);
			categoryRepo.save(category);
			return true;
		}
	}

	@Override
	public Category getCategoryById(Integer categoryId) {
		if(categoryId <1) {
			throw new ResourceNotFoundException("Please, Enter valid Category Id.", null);
		}		
		return categoryRepo.findById(categoryId)
				           .orElseThrow(()-> new ResourceNotFoundException("CategoryId", String.valueOf(categoryId)));
	}

	@Override
	public List<Category> getAllCategory() {		
		return categoryRepo.findAll();
	}

}
